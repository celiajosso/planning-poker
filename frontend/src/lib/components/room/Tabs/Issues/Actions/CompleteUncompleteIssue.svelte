<script lang="ts">
  import { CheckCircle } from "@steeze-ui/heroicons";
  import ButtonIcon from "$lib/ButtonIcon.svelte";
  import * as Tooltip from "$lib/components/ui/tooltip";
  import { Game } from "$lib/Game.svelte";

  let { selectedIssue = $bindable(), issue } = $props();
</script>

<Tooltip.Provider>
  <Tooltip.Root>
    <Tooltip.Trigger>
      {#if issue.isSaved}
        <ButtonIcon
          icon={CheckCircle}
          size="size-5"
          theme="solid"
          onclick={() => Game.saveStory()}
        ></ButtonIcon>
      {:else}
        <ButtonIcon
          icon={CheckCircle}
          size="size-5"
          theme="outline"
          onclick={() => Game.saveStory()}
        ></ButtonIcon>
      {/if}
    </Tooltip.Trigger>
    <Tooltip.Content>
      <p>Save Issue</p>
    </Tooltip.Content>
  </Tooltip.Root>
</Tooltip.Provider>
