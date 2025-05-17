<script lang="ts">
  import { ArrowUpTray } from "@steeze-ui/heroicons";

  import * as Tooltip from "$lib/components/ui/tooltip/index.js";
  import ButtonIcon from "$lib/ButtonIcon.svelte";

  import { handleFileChange } from "../../../../../routes/room/script";

  let fileInput: HTMLInputElement;
</script>

<Tooltip.Root>
  <Tooltip.Trigger>
    <ButtonIcon
      onclick={() => fileInput.click()}
      icon={ArrowUpTray}
      size="size-5"
      theme="solid"
    ></ButtonIcon>
  </Tooltip.Trigger>
  <Tooltip.Content>
    <p>Import CSV file</p>
  </Tooltip.Content>
</Tooltip.Root>
<input
  bind:this={fileInput}
  type="file"
  accept=".csv"
  onchange={handleFileChange}
  style="display: none;"
/>
