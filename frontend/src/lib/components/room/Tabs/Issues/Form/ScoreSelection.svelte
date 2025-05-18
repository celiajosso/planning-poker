<script lang="ts">
  import * as Select from "$lib/components/ui/select/index.js";
  import { ScrollArea } from "$lib/components/ui/scroll-area/index.js";

  import { scores } from "../../../../../../routes/room/script";
</script>

<Select.Root>
  <Select.Trigger class="w-[100px]">
    <Select.Value placeholder="Score" />
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
  <Select.Input name="score" />
</Select.Root>
