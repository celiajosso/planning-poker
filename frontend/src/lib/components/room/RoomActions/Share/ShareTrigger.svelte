<script>
    import {Share, Square2Stack} from "@steeze-ui/heroicons";

    import * as DropdownMenu from "$lib/components/ui/dropdown-menu";
    import * as Tooltip from "$lib/components/ui/tooltip";
    import ButtonIcon from "$lib/ButtonIcon.svelte";
</script>

<DropdownMenu.Trigger>
    <Tooltip.Provider>
        <Tooltip.Root>
            <Tooltip.Trigger>
                <ButtonIcon icon={Share} size="size-6" theme="outline"></ButtonIcon>
            </Tooltip.Trigger>
            <Tooltip.Content>
                <p>Share Room</p>
            </Tooltip.Content>
        </Tooltip.Root>
    </Tooltip.Provider>
</DropdownMenu.Trigger>
