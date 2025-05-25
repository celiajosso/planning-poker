<script lang="ts">
  import MinusIcon from "@lucide/svelte/icons/minus";
  import PlusIcon from "@lucide/svelte/icons/plus";
  import * as Drawer from "$lib/components/ui/drawer/index.js";
  import { Button, buttonVariants } from "$lib/components/ui/button/index.js";
  import { scaleBand, scaleLinear } from "d3-scale";

  let data = $state([
    { id: 1, goal: 400 },
    { id: 2, goal: 300 },
    { id: 3, goal: 200 },
    { id: 4, goal: 300 },
    { id: 5, goal: 200 },
    { id: 6, goal: 278 },
    { id: 7, goal: 189 },
    { id: 8, goal: 239 },
    { id: 9, goal: 300 },
    { id: 10, goal: 200 },
    { id: 11, goal: 278 },
    { id: 12, goal: 189 },
    { id: 13, goal: 349 },
  ]);

  let goal = $state(350);

  let width = 320;
  let height = 140;
  const margin = { top: 10, right: 10, bottom: 20, left: 30 };
  const innerWidth = width - margin.left - margin.right;
  const innerHeight = height - margin.top - margin.bottom;

  let xScale = $derived(
    scaleBand()
      .domain(data.map((d) => d.id.toString()))
      .range([0, innerWidth])
      .padding(0.3),
  );

  let yScale = $derived(scaleLinear().domain([0, 400]).range([innerHeight, 0]));

  function handleClick(adjustment: number) {
    goal = Math.max(200, Math.min(400, goal + adjustment));
  }
</script>

<Drawer.Root>
  <Drawer.Trigger class={buttonVariants({ variant: "outline" })}>
    Open Drawer
  </Drawer.Trigger>
  <Drawer.Content>
    <div class="mx-auto w-full max-w-sm">
      <Drawer.Header>
        <Drawer.Title>Move Goal</Drawer.Title>
        <Drawer.Description>Set your daily activity goal.</Drawer.Description>
      </Drawer.Header>
      <div class="p-4 pb-0">
        <div class="flex items-center justify-center space-x-2">
          <Button
            variant="outline"
            size="icon"
            class="size-8 shrink-0 rounded-full"
            onclick={() => handleClick(-10)}
            disabled={goal <= 200}
          >
            <MinusIcon />
            <span class="sr-only">Decrease</span>
          </Button>
          <div class="flex-1 text-center">
            <div class="text-7xl font-bold tracking-tighter">{goal}</div>
            <div class="text-muted-foreground text-[0.70rem] uppercase">
              Calories/day
            </div>
          </div>
          <Button
            variant="outline"
            size="icon"
            class="size-8 shrink-0 rounded-full"
            onclick={() => handleClick(10)}
            disabled={goal >= 400}
          >
            <PlusIcon />
            <span class="sr-only">Increase</span>
          </Button>
        </div>

        <div class="mt-3 h-[160px]">
          <svg {width} {height} class="block mx-auto">
            <g transform={`translate(${margin.left},${margin.top})`}>
              {#each [0, 100, 200, 300, 400] as tick}
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

              {#each data as d (d.id)}
                <rect
                  class="bar"
                  x={xScale(d.id.toString())}
                  y={yScale(d.goal)}
                  width={xScale.bandwidth()}
                  height={innerHeight - yScale(d.goal)}
                  fill={d.goal >= goal ? "var(--primary)" : "#ccc"}
                  style="transition: y 0.5s, height 0.5s, fill 0.5s"
                />
              {/each}
            </g>
          </svg>
        </div>
      </div>
      <Drawer.Footer>
        <Button>Submit</Button>
        <Drawer.Close class={buttonVariants({ variant: "outline" })}>
          Cancel
        </Drawer.Close>
      </Drawer.Footer>
    </div>
  </Drawer.Content>
</Drawer.Root>

<style>
  .bar {
    rx: 4;
    ry: 4;
  }
</style>
