<script lang="ts">
  import { ScrollArea } from "$lib/components/ui/scroll-area/index.js";
  import * as Select from "$lib/components/ui/select";

  import { scores } from "../../../../../../routes/room/script";

  let { value = $bindable() } = $props();

  const triggerContent = $derived(
    scores.find((f) => f.value === value)?.label ?? "Select a score",
  );
</script>

<Select.Root bind:value name="score" type="single">
  <Select.Trigger>
    {triggerContent}
  </Select.Trigger>
  <Select.Content>
    <Select.Group>
      <ScrollArea class="h-20">
        {#each scores as score}
          <Select.Item value={score.value} label={score.value}
            >{score.value}</Select.Item
          >
        {/each}
      </ScrollArea>
    </Select.Group>
  </Select.Content>
</Select.Root>
