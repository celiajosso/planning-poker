<script lang="ts">
    import {ArrowUpTray} from "@steeze-ui/heroicons";
    import ButtonIcon from "$lib/ButtonIcon.svelte";
    import * as Tooltip from "$lib/components/ui/tooltip";

    import {handleFileChange} from "../../../../../../routes/room/script";

    let fileInput: HTMLInputElement;
</script>

<Tooltip.Provider>
    <Tooltip.Root>
        <Tooltip.Trigger>
            <ButtonIcon
                    icon={ArrowUpTray}
                    onclick={() => fileInput.click()}
                    size="size-5"
                    theme="solid"
            ></ButtonIcon>
        </Tooltip.Trigger>
        <Tooltip.Content>
            <p>Import CSV file</p>
        </Tooltip.Content>
    </Tooltip.Root>
</Tooltip.Provider>

<input
        accept=".csv"
        bind:this={fileInput}
        onchange={handleFileChange}
        style="display: none;"
        type="file"
/>
