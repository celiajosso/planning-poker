<script lang="ts">
    import {cn, type WithElementRef} from "$lib/utils.js";
    import type {HTMLAttributes} from "svelte/elements";

    let {
        ref = $bindable(null),
        class: className,
        children,
        ...restProps
    }: WithElementRef<HTMLAttributes<HTMLDivElement>> = $props();
</script>

<div
        {...restProps}
        bind:this={ref}
        class={cn("mt-auto flex flex-col gap-2 p-4", className)}
        data-slot="sheet-footer"
>
    {@render children?.()}
</div>
