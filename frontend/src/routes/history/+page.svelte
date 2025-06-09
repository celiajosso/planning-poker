<script lang="ts">
  import ButtonIcon from "$lib/ButtonIcon.svelte";
  import * as Table from "$lib/components/ui/table/index.js";
  import { ChartBar } from "@steeze-ui/heroicons";
  import * as Drawer from "$lib/components/ui/drawer/index.js";
  import { Button } from "$lib/components/ui/button/index.js";
  import { scaleBand, scaleLinear } from "d3-scale";
  import BackToHome from "$lib/backToHome.svelte";

  type Issue = {
    title: string;
    description: string;
    participants: String[];
    votes: Record<string, number[]>;
    timestamp: number;
  };

  const issues: Issue[] = [
    {
      title: "Issue Title 1",
      description: "Issue Description",
      participants: ["Alice", "Bob", "Carol"],
      timestamp: Date.now(),
      votes: {
        Alice: [3, 5],
        Bob: [5, 5],
        Carol: [8, 5],
      },
    },
    {
      title: "Fix login bug",
      description: "Users can't log in when using Safari.",
      participants: ["Dave", "Eva"],
      timestamp: Date.now(),
      votes: { Dave: [2, 3], Eva: [3, 3] },
    },
    {
      title: "Add dark mode",
      description: "Implement a dark theme for the app UI.",
      participants: ["Frank", "Grace", "Helen"],
      timestamp: Date.now(),
      votes: { Frank: [8, 8, 8], Grace: [5, 6, 7], Helen: [8, 8, 8] },
    },
    {
      title: "Optimize database queries",
      description: "Improve performance of user-related queries.",
      participants: ["Ivan", "Jack"],
      timestamp: Date.now(),
      votes: { Ivan: [13, 13], Jack: [8, 13] },
    },
    {
      title: "Refactor legacy payment module",
      description: "Clean up and document the old payment processing code.",
      participants: ["Kara", "Leo", "Mia"],
      timestamp: Date.now(),
      votes: { Kara: [8, 8], Leo: [5, 8], Mia: [8, 8] },
    },
    {
      title: "Redesign landing page",
      description: "Create a new responsive design for the homepage.",
      participants: ["Alice", "Bob", "Clara", "Dylan", "Elena"],
      timestamp: Date.now(),
      votes: {
        Alice: [5, 8, 8],
        Bob: [8, 8, 8],
        Clara: [8, 8, 8],
        Dylan: [5, 5, 8],
        Elena: [8, 8, 8],
      },
    },
    {
      title: "Improve caching mechanism",
      description: "Add Redis caching to reduce database load.",
      participants: ["Fabien", "Giulia", "Hugo", "Isabelle", "Jules", "Katia"],
      timestamp: Date.now(),
      votes: {
        Fabien: [13, 13],
        Giulia: [8, 13],
        Hugo: [13, 13],
        Isabelle: [8, 13],
        Jules: [13, 13],
        Katia: [13, 13],
      },
    },
    {
      title: "Integrate payment gateway",
      description: "Support for Stripe and PayPal payments.",
      timestamp: Date.now(),
      participants: ["Leo", "Mona", "Nicolas", "Omar", "Patricia", "Quentin"],
      votes: {
        Leo: [8, 8],
        Mona: [5, 8],
        Nicolas: [8, 8],
        Omar: [8, 8],
        Patricia: [8, 8],
        Quentin: [8, 8],
      },
    },
    {
      title: "Add real-time chat",
      description: "Allow users to message each other in real-time.",
      participants: ["Rania", "Sami", "Théo", "Ursula", "Victor"],
      timestamp: Date.now(),
      votes: {
        Rania: [8, 13, 13],
        Sami: [13, 13, 13],
        Théo: [13, 13, 13],
        Ursula: [13, 13, 13],
        Victor: [5, 8, 13],
      },
    },
  ];

  let selectedIssue: Issue | null = null;
  let isDrawerOpen = false;

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

  let votesDistribution: { score: number; count: number }[] = [];
  let participants: string[] = [];
  let rounds = 0;
  let mean = 0;
  let median = 0;
  let stdDev = 0;
  let convergenceRound: number | null = null;

  $: if (selectedIssue) {
    rounds = Object.values(selectedIssue.votes)[0]?.length ?? 0;
    participants = Object.keys(selectedIssue.votes);

    const firstVotes = Object.values(selectedIssue.votes).map((v) => v[0]);

    mean = firstVotes.reduce((a, b) => a + b, 0) / firstVotes.length;

    const sorted = [...firstVotes].sort((a, b) => a - b);
    const mid = Math.floor(sorted.length / 2);
    median =
      sorted.length % 2 === 0
        ? (sorted[mid - 1] + sorted[mid]) / 2
        : sorted[mid];

    stdDev = Math.sqrt(
      firstVotes.map((x) => Math.pow(x - mean, 2)).reduce((a, b) => a + b, 0) /
        firstVotes.length,
    );

    const finalRoundVotes = Object.values(selectedIssue.votes).map((v) =>
      v.at(-1),
    );
    const allSame = finalRoundVotes.every(
      (vote) => vote === finalRoundVotes[0],
    );
    convergenceRound = allSame ? rounds : null;

    const distribution = new Map<number, number>();
    for (let i = 0; i <= 13; i++) {
      distribution.set(i, 0);
    }

    firstVotes.forEach((vote) => {
      const current = distribution.get(vote) || 0;
      distribution.set(vote, current + 1);
    });

    votesDistribution = Array.from(distribution.entries()).map(
      ([score, count]) => ({
        score,
        count,
      }),
    );
  }

  $: xScale = scaleBand()
    .domain(votesDistribution.map((d) => d.score.toString()))
    .range([0, innerWidth])
    .padding(0.1);

  $: yScale = scaleLinear()
    .domain([0, Math.max(...votesDistribution.map((d) => d.count), 1)])
    .range([innerHeight, 0])
    .nice();
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
            <svg {width} {height} class="block mx-auto mb-4">
              <g transform={`translate(${margin.left},${margin.top})`}>
                {#each Array.from({ length: Math.max(...votesDistribution.map((d) => d.count), 1) + 1 }, (_, i) => i) as tick}
                  <line
                    x1="0"
                    x2={innerWidth}
                    y1={yScale(tick)}
                    y2={yScale(tick)}
                    stroke="#ddd"
                    stroke-width="1"
                  />
                  <text
                    x="-10"
                    y={yScale(tick)}
                    text-anchor="end"
                    alignment-baseline="middle"
                    font-size="10"
                    fill="#666"
                  >
                    {tick}
                  </text>
                {/each}

                {#each votesDistribution as d}
                  <text
                    x={xScale(d.score.toString()) + xScale.bandwidth() / 2}
                    y={innerHeight + 15}
                    text-anchor="middle"
                    font-size="10"
                    fill="#666"
                  >
                    {d.score}
                  </text>
                {/each}

                {#each votesDistribution as d}
                  {#if d.count > 0}
                    <rect
                      x={xScale(d.score.toString())}
                      y={yScale(d.count)}
                      width={xScale.bandwidth()}
                      height={innerHeight - yScale(d.count)}
                      fill="#000"
                      stroke="#000"
                      stroke-width="1"
                    />
                    <text
                      x={xScale(d.score.toString()) + xScale.bandwidth() / 2}
                      y={yScale(d.count) - 5}
                      text-anchor="middle"
                      font-size="10"
                      fill="#333"
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
                  fill="#333"
                  font-weight="bold"
                >
                  Score
                </text>
                <text
                  x="-25"
                  y={innerHeight / 2}
                  text-anchor="middle"
                  font-size="12"
                  fill="#333"
                  font-weight="bold"
                  transform={`rotate(-90, -25, ${innerHeight / 2})`}
                >
                  Number of Votes
                </text>
              </g>
            </svg>

            <div class="space-y-2 text-sm pb-4">
              <p>
                <strong>Participants:</strong>
                {participants.join(", ")}
              </p>
              <p><strong>Rounds:</strong> {rounds}</p>
              <p><strong>Median:</strong> {median}</p>
              <p><strong>Standard Deviation:</strong> {stdDev.toFixed(2)}</p>
              <p>
                <strong>Convergence:</strong>
                {convergenceRound ? `Yes (round ${convergenceRound})` : "No"}
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
