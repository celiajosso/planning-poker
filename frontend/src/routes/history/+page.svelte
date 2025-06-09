<script lang="ts">
  import ButtonIcon from "$lib/ButtonIcon.svelte";
  import * as Table from "$lib/components/ui/table/index.js";
  import { ChartBar } from "@steeze-ui/heroicons";
  import * as Drawer from "$lib/components/ui/drawer/index.js";
  import { Button } from "$lib/components/ui/button/index.js";
  import { scaleBand, scaleLinear } from "d3-scale";
  import BackToHome from "$lib/backToHome.svelte";

  import { username } from "$lib/utils";
  import { onMount } from "svelte";

  type Issue = {
    id: string;
    title: string;
    description?: string;
    timestamp: number;
    votes: Record<string, number[]>;
  };

  let issues: Issue[] = $state([]);
  let selectedIssue: Issue | null = $state(null);
  let isDrawerOpen = $state(false);

  let width = 300;
  let height = 200;
  const margin = { top: 20, right: 20, bottom: 40, left: 40 };
  const innerWidth = width - margin.left - margin.right;
  const innerHeight = height - margin.top - margin.bottom;

  function openDrawer(issue: Issue) {
    selectedIssue = issue;
    isDrawerOpen = true;
  }

  function closeDrawer() {
    selectedIssue = null;
    isDrawerOpen = false;
  }

  onMount(async () => {
    try {
      const response = await fetch(
        `${window.location.protocol}//${import.meta.env.PROD ? window.location.host : "localhost:8080"}/api/history?username=${$username}`,
      );
      if (!response.ok) throw new Error("Failed to fetch issues");
      const data = await response.json();
      issues = data;
    } catch (error) {
      console.error("Error loading issues:", error);
    }
  });

  let firstRoundDistribution: { score: number; count: number }[] = $state([]);
  let lastRoundDistribution: { score: number; count: number }[] = $state([]);
  let participants: string[] = $state([]);
  let rounds = $state(0);
  let meanFirst = $state(0);
  let medianFirst = $state(0);
  let stdDevFirst = $state(0);
  let meanLast = $state(0);
  let medianLast = $state(0);
  let stdDevLast = $state(0);
  let convergenceRound: number | null = $state(null);

  $effect(() => {
    if (selectedIssue) {
      rounds = Object.values(selectedIssue.votes)[0]?.length ?? 0;
      participants = Object.keys(selectedIssue.votes);

      const firstVotes = Object.values(selectedIssue.votes).map((v) => v[0]);
      const lastVotes = Object.values(selectedIssue.votes).map(
        (v) => v.at(-1) ?? 0,
      );

      meanFirst = firstVotes.reduce((a, b) => a + b, 0) / firstVotes.length;
      meanLast = lastVotes.reduce((a, b) => a + b, 0) / lastVotes.length;

      const sortedFirst = [...firstVotes].sort((a, b) => a - b);
      const midFirst = Math.floor(sortedFirst.length / 2);
      medianFirst =
        sortedFirst.length % 2 === 0
          ? (sortedFirst[midFirst - 1] + sortedFirst[midFirst]) / 2
          : sortedFirst[midFirst];

      const sortedLast = [...lastVotes].sort((a, b) => a - b);
      const midLast = Math.floor(sortedLast.length / 2);
      medianLast =
        sortedLast.length % 2 === 0
          ? (sortedLast[midLast - 1] + sortedLast[midLast]) / 2
          : sortedLast[midLast];

      stdDevFirst = Math.sqrt(
        firstVotes
          .map((x) => Math.pow(x - meanFirst, 2))
          .reduce((a, b) => a + b, 0) / firstVotes.length,
      );

      stdDevLast = Math.sqrt(
        lastVotes
          .map((x) => Math.pow(x - meanLast, 2))
          .reduce((a, b) => a + b, 0) / lastVotes.length,
      );

      const allSame = lastVotes.every((vote) => vote === lastVotes[0]);
      convergenceRound = allSame ? rounds : null;

      const firstDistribution = new Map<number, number>();
      for (let i = 0; i <= 13; i++) {
        firstDistribution.set(i, 0);
      }
      firstVotes.forEach((vote) => {
        const current = firstDistribution.get(vote) || 0;
        firstDistribution.set(vote, current + 1);
      });
      firstRoundDistribution = Array.from(firstDistribution.entries()).map(
        ([score, count]) => ({ score, count }),
      );

      const lastDistribution = new Map<number, number>();
      for (let i = 0; i <= 13; i++) {
        lastDistribution.set(i, 0);
      }
      lastVotes.forEach((vote) => {
        const current = lastDistribution.get(vote) || 0;
        lastDistribution.set(vote, current + 1);
      });
      lastRoundDistribution = Array.from(lastDistribution.entries()).map(
        ([score, count]) => ({ score, count }),
      );
    }
  });

  let firstRoundXScale = $derived(
    scaleBand()
      .domain(firstRoundDistribution.map((d) => d.score.toString()))
      .range([0, innerWidth])
      .padding(0.1),
  );

  let firstRoundYScale = $derived(
    scaleLinear()
      .domain([0, Math.max(...firstRoundDistribution.map((d) => d.count), 1)])
      .range([innerHeight, 0])
      .nice(),
  );

  let lastRoundXScale = $derived(
    scaleBand()
      .domain(lastRoundDistribution.map((d) => d.score.toString()))
      .range([0, innerWidth])
      .padding(0.1),
  );

  let lastRoundYScale = $derived(
    scaleLinear()
      .domain([0, Math.max(...lastRoundDistribution.map((d) => d.count), 1)])
      .range([innerHeight, 0])
      .nice(),
  );
</script>

<div
  class="w-dvw text-[#333] h-dvh items-center justify-center bg-[#f4f4f9] p-10"
>
  <BackToHome />
  <h1 class="text-4xl font-bold text-center">History</h1>
  <p class="my-4 text-center">You can access your history here</p>

  <Table.Root>
    <Table.Header>
      <Table.Row>
        <Table.Head>Title</Table.Head>
        <Table.Head>Description</Table.Head>
        <Table.Head>Final Estimate</Table.Head>
        <Table.Head>Date</Table.Head>
        <Table.Head>Charts</Table.Head>
      </Table.Row>
    </Table.Header>
    <Table.Body>
      {#each issues as issue (issue.title)}
        <Table.Row>
          <Table.Cell>{issue.title}</Table.Cell>
          <Table.Cell>{issue.description}</Table.Cell>

          <Table.Cell>
            {#if Object.keys(issue.votes).length > 0}
              {(
                Object.values(issue.votes)
                  .map((v) => v.at(-1) ?? 0)
                  .reduce((a, b) => a + b, 0) /
                Object.values(issue.votes).length
              ).toFixed(1)}
            {:else}
              N/A
            {/if}
          </Table.Cell>
          <Table.Cell>{new Date(issue.timestamp).toLocaleString()}</Table.Cell>

          <Table.Cell>
            <ButtonIcon
              onclick={() => openDrawer(issue)}
              icon={ChartBar}
              size="size-5"
              theme="outline"
            />
          </Table.Cell>
        </Table.Row>
      {/each}
    </Table.Body>
  </Table.Root>
</div>

{#if isDrawerOpen && selectedIssue}
  <Drawer.Root bind:open={isDrawerOpen}>
    <Drawer.Content class="h-[90vh]">
      <div class="mx-auto w-full max-w-lg h-full flex flex-col">
        <Drawer.Header class="flex-shrink-0">
          <Drawer.Title>{selectedIssue.title}</Drawer.Title>
          <Drawer.Description
            >Details and statistics for this issue.</Drawer.Description
          >
        </Drawer.Header>

        <div class="flex-1 overflow-hidden p-4 pt-0">
          <div class="h-full overflow-y-auto overflow-x-hidden">
            <div class="space-y-2 text-sm pb-4">
              <p>
                <strong>Participants:</strong>
                {participants.join(", ")}
              </p>
              <p><strong>Rounds:</strong> {rounds}</p>

              <p>
                <strong>Convergence:</strong>
                {convergenceRound ? `Yes` : "No"}
              </p>
            </div>
            <h3 class="text-center font-semibold mb-2 pt-5">
              First Round Distribution
            </h3>
            <svg {width} {height} class="block mx-auto mb-4">
              <g transform={`translate(${margin.left},${margin.top})`}>
                {#each Array.from({ length: Math.max(...firstRoundDistribution.map((d) => d.count), 1) + 1 }, (_, i) => i) as tick}
                  <line
                    x1="0"
                    x2={innerWidth}
                    y1={firstRoundYScale(tick)}
                    y2={firstRoundYScale(tick)}
                    stroke="#ddd"
                    stroke-width="1"
                  />
                  <text
                    x="-10"
                    y={firstRoundYScale(tick)}
                    text-anchor="end"
                    alignment-baseline="middle"
                    font-size="10"
                    fill="#666"
                  >
                    {tick}
                  </text>
                {/each}

                {#each firstRoundDistribution as d}
                  <text
                    x={firstRoundXScale(d.score.toString()) +
                      firstRoundXScale.bandwidth() / 2}
                    y={innerHeight + 15}
                    text-anchor="middle"
                    font-size="10"
                    fill="#666"
                  >
                    {d.score}
                  </text>
                {/each}

                {#each firstRoundDistribution as d}
                  {#if d.count > 0}
                    <rect
                      x={firstRoundXScale(d.score.toString())}
                      y={firstRoundYScale(d.count)}
                      width={firstRoundXScale.bandwidth()}
                      height={innerHeight - firstRoundYScale(d.count)}
                    />
                    <text
                      x={firstRoundXScale(d.score.toString()) +
                        firstRoundXScale.bandwidth() / 2}
                      y={firstRoundYScale(d.count) - 5}
                      text-anchor="middle"
                      font-size="10"
                    >
                      {d.count}
                    </text>
                  {/if}
                {/each}

                <text
                  x={innerWidth / 2}
                  y={innerHeight + 35}
                  text-anchor="middle"
                  font-size="12"
                  font-weight="bold"
                >
                  Score
                </text>
                <text
                  x="-25"
                  y={innerHeight / 2}
                  text-anchor="middle"
                  font-size="12"
                  font-weight="bold"
                  transform={`rotate(-90, -25, ${innerHeight / 2})`}
                >
                  Number of Votes
                </text>
              </g>
            </svg>

            <div class="space-y-2 text-sm pb-4">
              <p><strong>Mean:</strong> {meanFirst.toFixed(2)}</p>
              <p><strong>Median:</strong> {medianFirst}</p>
              <p>
                <strong>Standard Deviation:</strong>
                {stdDevFirst.toFixed(2)}
              </p>
            </div>

            <h3 class="text-center font-semibold mb-2 pt-5">
              Last Round Distribution
            </h3>
            <svg {width} {height} class="block mx-auto mb-4">
              <g transform={`translate(${margin.left},${margin.top})`}>
                {#each Array.from({ length: Math.max(...lastRoundDistribution.map((d) => d.count), 1) + 1 }, (_, i) => i) as tick}
                  <line
                    x1="0"
                    x2={innerWidth}
                    y1={lastRoundYScale(tick)}
                    y2={lastRoundYScale(tick)}
                    stroke="#ddd"
                    stroke-width="1"
                  />
                  <text
                    x="-10"
                    y={lastRoundYScale(tick)}
                    text-anchor="end"
                    alignment-baseline="middle"
                    font-size="10"
                    fill="#666"
                  >
                    {tick}
                  </text>
                {/each}

                {#each lastRoundDistribution as d}
                  <text
                    x={lastRoundXScale(d.score.toString()) +
                      lastRoundXScale.bandwidth() / 2}
                    y={innerHeight + 15}
                    text-anchor="middle"
                    font-size="10"
                  >
                    {d.score}
                  </text>
                {/each}

                {#each lastRoundDistribution as d}
                  {#if d.count > 0}
                    <rect
                      x={lastRoundXScale(d.score.toString())}
                      y={lastRoundYScale(d.count)}
                      width={lastRoundXScale.bandwidth()}
                      height={innerHeight - lastRoundYScale(d.count)}
                    />
                    <text
                      x={lastRoundXScale(d.score.toString()) +
                        lastRoundXScale.bandwidth() / 2}
                      y={lastRoundYScale(d.count) - 5}
                      text-anchor="middle"
                      font-size="10"
                    >
                      {d.count}
                    </text>
                  {/if}
                {/each}

                <text
                  x={innerWidth / 2}
                  y={innerHeight + 35}
                  text-anchor="middle"
                  font-size="12"
                  font-weight="bold"
                >
                  Score
                </text>
                <text
                  x="-25"
                  y={innerHeight / 2}
                  text-anchor="middle"
                  font-size="12"
                  font-weight="bold"
                  transform={`rotate(-90, -25, ${innerHeight / 2})`}
                >
                  Number of Votes
                </text>
              </g>
            </svg>

            <div class="space-y-2 text-sm pb-4">
              <p><strong>Mean:</strong> {meanLast.toFixed(2)}</p>
              <p><strong>Median:</strong> {medianLast}</p>
              <p>
                <strong>Standard Deviation:</strong>
                {stdDevLast.toFixed(2)}
              </p>
            </div>
          </div>
        </div>

        <Drawer.Footer class="flex-shrink-0">
          <Button class="mb-5" onclick={closeDrawer}>Close</Button>
        </Drawer.Footer>
      </div>
    </Drawer.Content>
  </Drawer.Root>
{/if}
