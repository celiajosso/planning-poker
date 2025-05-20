<script lang="ts">
    import {Tabs as TabsPrimitive} from "bits-ui";
    import {cn} from "$lib/utils.js";

    let {
        ref = $bindable(null),
        class: className,
        ...restProps
    }: TabsPrimitive.ContentProps = $props();
</script>

<TabsPrimitive.Content
        {...restProps}
        bind:ref
        class={cn("flex-1 outline-none", className)}
        data-slot="tabs-content"
/>
