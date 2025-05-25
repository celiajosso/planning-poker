<script lang="ts">
  import { CheckCircle } from "@steeze-ui/heroicons";
  import ButtonIcon from "$lib/ButtonIcon.svelte";
  import * as Tooltip from "$lib/components/ui/tooltip";

  let { selectedIssue = $bindable(), issue } = $props();
</script>

<Tooltip.Provider>
  <Tooltip.Root>
    <Tooltip.Trigger>
      <ButtonIcon icon={CheckCircle} size="size-5" theme="outline"></ButtonIcon>
    </Tooltip.Trigger>
    <Tooltip.Content>
      <p>Save Issue</p>
    </Tooltip.Content>
  </Tooltip.Root>
</Tooltip.Provider>
