<script lang="ts">
    import {cn, type WithElementRef} from "$lib/utils.js";
    import type {HTMLAttributes} from "svelte/elements";

    let {
        ref = $bindable(null),
        class: className,
        children,
        ...restProps
    }: WithElementRef<HTMLAttributes<HTMLElement>> = $props();
</script>

<caption
        {...restProps}
        bind:this={ref}
        class={cn("text-muted-foreground mt-4 text-sm", className)}
        data-slot="table-caption"
>
    {@render children?.()}
</caption>
