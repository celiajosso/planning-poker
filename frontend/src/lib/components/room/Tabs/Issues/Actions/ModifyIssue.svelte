<script lang="ts">
  import { PencilSquare } from "@steeze-ui/heroicons";
  import ButtonIcon from "$lib/ButtonIcon.svelte";
  import * as Tooltip from "$lib/components/ui/tooltip";

  let {
    isModifyOpen = $bindable(),
    selectedIssue = $bindable(),
    issue,
  } = $props();
</script>

<Tooltip.Provider>
  <Tooltip.Root>
    <Tooltip.Trigger>
      <ButtonIcon
        icon={PencilSquare}
        onclick={() => {
          selectedIssue = issue;
          isModifyOpen = true;
        }}
        size="size-5"
        theme="outline"
      ></ButtonIcon>
    </Tooltip.Trigger>
    <Tooltip.Content>
      <p>Modify</p>
    </Tooltip.Content>
  </Tooltip.Root>
</Tooltip.Provider>
