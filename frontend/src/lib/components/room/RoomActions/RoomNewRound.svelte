<script>
  import { Game } from "$lib/Game.svelte";

  import { Plus } from "@steeze-ui/heroicons";

  import * as Tooltip from "$lib/components/ui/tooltip";
  import ButtonIcon from "$lib/ButtonIcon.svelte";
</script>

{#if Game.storage.user.role == "Administrator"}
  <Tooltip.Provider>
    <Tooltip.Root>
      <Tooltip.Trigger>
        <ButtonIcon
          onclick={() => {
            Game.newRoundStory();
          }}
          icon={Plus}
          size="size-6"
          theme="solid"
        />
      </Tooltip.Trigger>
      <Tooltip.Content><p>New Round</p></Tooltip.Content>
    </Tooltip.Root>
  </Tooltip.Provider>
{/if}
