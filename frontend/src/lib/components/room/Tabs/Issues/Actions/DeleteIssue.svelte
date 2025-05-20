<script lang="ts">
  import { Trash } from "@steeze-ui/heroicons";
  import ButtonIcon from "$lib/ButtonIcon.svelte";
  import * as Tooltip from "$lib/components/ui/tooltip";

  let {
    isDeleteOpen = $bindable(),
    selectedIssue = $bindable(),
    issue,
  } = $props();
</script>

<Tooltip.Provider>
  <Tooltip.Root>
    <Tooltip.Trigger>
      <ButtonIcon
        icon={Trash}
        onclick={() => {
          selectedIssue = issue;
          isDeleteOpen = true;
        }}
        size="size-5"
        theme="outline"
      ></ButtonIcon>
    </Tooltip.Trigger>
    <Tooltip.Content>
      <p>Delete</p>
    </Tooltip.Content>
  </Tooltip.Root>
</Tooltip.Provider>
