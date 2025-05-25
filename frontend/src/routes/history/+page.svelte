<script lang="ts">
  import ButtonIcon from "$lib/ButtonIcon.svelte";
  import * as Table from "$lib/components/ui/table/index.js";
  import { ChartBar } from "@steeze-ui/heroicons";
  import * as Drawer from "$lib/components/ui/drawer/index.js";
  import { Button } from "$lib/components/ui/button/index.js";
  import { scaleBand, scaleLinear } from "d3-scale";
  import BackToHome from "$lib/backToHome.svelte";

  type Vote = {
    user: string;
    role: string;
    values: number[];
  };

  type Issue = {
    title: string;
    description: string;
    roomId: string;
    lastModified: string;
    finalEstimate: number;
    votes: Vote[];
  };

  const issues = [
    {
      title: "Issue Title 1",
      description: "Issue Description",
      roomId: "Room 1",
      lastModified: "2025-01-01",
      finalEstimate: 5,
      votes: [
        { user: "Alice", role: "Dev", values: [3, 5] },
        { user: "Bob", role: "PO", values: [5, 5] },
        { user: "Carol", role: "Dev", values: [8, 5] },
      ],
    },
    {
      title: "Fix login bug",
      description: "Users can't log in when using Safari.",
      roomId: "Room 2",
      lastModified: "2025-02-10",
      finalEstimate: 3,
      votes: [
        { user: "Dave", role: "Dev", values: [2, 3] },
        { user: "Eva", role: "PO", values: [3, 3] },
      ],
    },
    {
      title: "Add dark mode",
      description: "Implement a dark theme for the app UI.",
      roomId: "Room 3",
      lastModified: "2025-03-15",
      finalEstimate: 8,
      votes: [
        { user: "Frank", role: "Dev", values: [8, 8, 8] },
        { user: "Grace", role: "Designer", values: [5, 6, 7] },
        { user: "Helen", role: "PO", values: [8, 8, 8] },
      ],
    },
    {
      title: "Optimize database queries",
      description: "Improve performance of user-related queries.",
      roomId: "Room 1",
      lastModified: "2025-04-01",
      finalEstimate: 13,
      votes: [
        { user: "Ivan", role: "Dev", values: [13, 13] },
        { user: "Jack", role: "DevOps", values: [8, 13] },
      ],
    },
    {
      title: "Refactor legacy payment module",
      description: "Clean up and document the old payment processing code.",
      roomId: "Room 2",
      lastModified: "2025-05-20",
      finalEstimate: 8,
      votes: [
        { user: "Kara", role: "Dev", values: [8, 8] },
        { user: "Leo", role: "PO", values: [5, 8] },
        { user: "Mia", role: "QA", values: [8, 8] },
      ],
    },
    {
      title: "Redesign landing page",
      description: "Create a new responsive design for the homepage.",
      roomId: "UX Room",
      lastModified: "2025-05-10",
      finalEstimate: 8,
      votes: [
        { user: "Alice", role: "Designer", values: [5, 8, 8] },
        { user: "Bob", role: "Dev", values: [8, 8, 8] },
        { user: "Clara", role: "Dev", values: [8, 8, 8] },
        { user: "Dylan", role: "QA", values: [5, 5, 8] },
        { user: "Elena", role: "PO", values: [8, 8, 8] },
      ],
    },
    {
      title: "Improve caching mechanism",
      description: "Add Redis caching to reduce database load.",
      roomId: "Backend",
      lastModified: "2025-05-12",
      finalEstimate: 13,
      votes: [
        { user: "Fabien", role: "Dev", values: [13, 13] },
        { user: "Giulia", role: "Dev", values: [8, 13] },
        { user: "Hugo", role: "DevOps", values: [13, 13] },
        { user: "Isabelle", role: "QA", values: [8, 13] },
        { user: "Jules", role: "PO", values: [13, 13] },
        { user: "Katia", role: "Scrum Master", values: [13, 13] },
      ],
    },
    {
      title: "Integrate payment gateway",
      description: "Support for Stripe and PayPal payments.",
      roomId: "E-commerce",
      lastModified: "2025-05-18",
      finalEstimate: 8,
      votes: [
        { user: "Leo", role: "Dev", values: [8, 8] },
        { user: "Mona", role: "Dev", values: [5, 8] },
        { user: "Nicolas", role: "PO", values: [8, 8] },
        { user: "Omar", role: "QA", values: [8, 8] },
        { user: "Patricia", role: "Security", values: [8, 8] },
        { user: "Quentin", role: "PM", values: [8, 8] },
      ],
    },
    {
      title: "Add real-time chat",
      description: "Allow users to message each other in real-time.",
      roomId: "Messaging",
      lastModified: "2025-05-22",
      finalEstimate: 13,
      votes: [
        { user: "Rania", role: "Dev", values: [8, 13, 13] },
        { user: "Sami", role: "Dev", values: [13, 13, 13] },
        { user: "Théo", role: "Dev", values: [13, 13, 13] },
        { user: "Ursula", role: "PO", values: [13, 13, 13] },
        { user: "Victor", role: "UX", values: [5, 8, 13] },
      ],
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
  let participantsWithRoles: string[] = [];
  let rounds = 0;
  let mean = 0;
  let median = 0;
  let stdDev = 0;
  let convergenceRound: number | null = null;

  $: if (selectedIssue) {
    rounds = selectedIssue.votes[0]?.values.length ?? 0;
    participantsWithRoles = selectedIssue.votes.map(
      (v) => `${v.user} (${v.role})`,
    );

    const firstVotes = selectedIssue.votes.map((v) => v.values[0]);

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

    const finalRoundVotes = selectedIssue.votes.map(
      (v) => v.values[v.values.length - 1],
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
        <Table.Head>Room ID</Table.Head>
        <Table.Head>Charts</Table.Head>
      </Table.Row>
    </Table.Header>
    <Table.Body>
      {#each issues as issue (issue.title)}
        <Table.Row>
          <Table.Cell>{issue.title}</Table.Cell>
          <Table.Cell>{issue.description}</Table.Cell>
          <Table.Cell>{issue.finalEstimate}</Table.Cell>
          <Table.Cell>{issue.roomId}</Table.Cell>
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
                {participantsWithRoles.join(", ")}
              </p>
              <p><strong>Rounds:</strong> {rounds}</p>
              <p><strong>Mean:</strong> {mean.toFixed(2)}</p>
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
