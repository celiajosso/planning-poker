<script>
  import { Game } from "../../../Game.svelte";
  import { goto } from "$app/navigation";
  import { ArrowRightEndOnRectangle } from "@steeze-ui/heroicons";

  import * as Tooltip from "$lib/components/ui/tooltip";
  import ButtonIcon from "$lib/ButtonIcon.svelte";
</script>

<Tooltip.Provider>
  <Tooltip.Root>
    <Tooltip.Trigger>
      <ButtonIcon
        onclick={() => {
          Game.quitRoom();
          goto("/");
        }}
        icon={ArrowRightEndOnRectangle}
        size="size-6"
        theme="solid"
      />
    </Tooltip.Trigger>
    <Tooltip.Content><p>Exit Room</p></Tooltip.Content>
  </Tooltip.Root>
</Tooltip.Provider>
