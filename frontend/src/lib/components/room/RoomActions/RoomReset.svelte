<script>
  import { Game } from "../../../Game.svelte";
  import { ArrowPath } from "@steeze-ui/heroicons";

  import * as Tooltip from "$lib/components/ui/tooltip";
  import ButtonIcon from "$lib/ButtonIcon.svelte";
</script>

<Tooltip.Provider>
  <Tooltip.Root>
    <Tooltip.Trigger>
      <ButtonIcon
        onclick={Game.restart}
        icon={ArrowPath}
        size="size-6"
        theme="solid"
      />
    </Tooltip.Trigger>
    <Tooltip.Content><p>Reset animation</p></Tooltip.Content>
  </Tooltip.Root>
</Tooltip.Provider>
