<script lang="ts">
    import {Game} from "$lib/Game.svelte";
    import {PaperAirplane} from "@steeze-ui/heroicons";
    import ButtonIcon from "$lib/ButtonIcon.svelte";
    import * as Tooltip from "$lib/components/ui/tooltip";

    let {issue} = $props();
</script>

<Tooltip.Provider>
    <Tooltip.Root>
        <Tooltip.Trigger>
            <ButtonIcon
                    icon={PaperAirplane}
                    onclick={() => Game.selectStory(issue.id)}
                    size="size-5"
                    theme="outline"
            ></ButtonIcon>
        </Tooltip.Trigger>
        <Tooltip.Content>
            <p>Vote</p>
        </Tooltip.Content>
    </Tooltip.Root>
</Tooltip.Provider>
