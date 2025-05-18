<script lang="ts">
  import { Game } from "$lib/Game.svelte";
  import { PaperAirplane } from "@steeze-ui/heroicons";

  import * as Tooltip from "$lib/components/ui/tooltip/index.js";
  import ButtonIcon from "$lib/ButtonIcon.svelte";

  let { issue } = $props();
</script>

<Tooltip.Root>
  <Tooltip.Trigger>
    <ButtonIcon
      onclick={() => Game.selectStory(issue.id)}
      icon={PaperAirplane}
      size="size-5"
      theme="outline"
    ></ButtonIcon>
  </Tooltip.Trigger>
  <Tooltip.Content>
    <p>Vote</p>
  </Tooltip.Content>
</Tooltip.Root>
