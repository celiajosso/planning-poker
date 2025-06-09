<script lang="ts">
  import { CheckCircle } from "@steeze-ui/heroicons";
  import ButtonIcon from "$lib/ButtonIcon.svelte";
  import * as Tooltip from "$lib/components/ui/tooltip";
  import { Game } from "$lib/Game.svelte";

  let { selectedIssue = $bindable(), issue } = $props();
  import { isLogged } from "$lib/utils";
</script>

{#if issue.isSaved}
  <Tooltip.Provider>
    <Tooltip.Root>
      <Tooltip.Trigger>
        <ButtonIcon
          icon={CheckCircle}
          size="size-5"
          theme="solid"
          onclick={() => Game.saveStory()}
        ></ButtonIcon>
      </Tooltip.Trigger>
      <Tooltip.Content>
        <p>Save Issue</p>
      </Tooltip.Content>
    </Tooltip.Root>
  </Tooltip.Provider>
{:else}
  <Tooltip.Provider>
    <Tooltip.Root>
      <Tooltip.Trigger>
        <ButtonIcon
          icon={CheckCircle}
          size="size-5"
          theme="outline"
          onclick={() => Game.saveStory()}
        ></ButtonIcon>
      </Tooltip.Trigger>
      <Tooltip.Content>
        <p>Save Issue</p>
      </Tooltip.Content>
    </Tooltip.Root>
  </Tooltip.Provider>
{/if}
