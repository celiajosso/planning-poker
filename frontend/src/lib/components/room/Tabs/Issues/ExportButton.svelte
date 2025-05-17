<script lang="ts">
  import { exportToCSV } from "$lib/CSV.svelte";
  import { ArrowDownTray } from "@steeze-ui/heroicons";

  import * as Tooltip from "$lib/components/ui/tooltip/index.js";
  import ButtonIcon from "$lib/ButtonIcon.svelte";
</script>

<Tooltip.Root>
  <Tooltip.Trigger>
    <ButtonIcon
      onclick={exportToCSV}
      icon={ArrowDownTray}
      size="size-5"
      theme="solid"
    ></ButtonIcon>
  </Tooltip.Trigger>
  <Tooltip.Content>
    <p>Export CSV file</p>
  </Tooltip.Content>
</Tooltip.Root>
