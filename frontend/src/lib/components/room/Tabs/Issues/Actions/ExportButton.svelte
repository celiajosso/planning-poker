<script lang="ts">
  import { exportToCSV } from "$lib/CSV.svelte";
  import { ArrowDownTray } from "@steeze-ui/heroicons";
  import ButtonIcon from "$lib/ButtonIcon.svelte";
  import * as Tooltip from "$lib/components/ui/tooltip";
</script>

<Tooltip.Provider>
  <Tooltip.Root>
    <Tooltip.Trigger>
      <ButtonIcon
        icon={ArrowDownTray}
        onclick={exportToCSV}
        size="size-5"
        theme="solid"
      ></ButtonIcon>
    </Tooltip.Trigger>
    <Tooltip.Content>
      <p>Export CSV file</p>
    </Tooltip.Content>
  </Tooltip.Root>
</Tooltip.Provider>
