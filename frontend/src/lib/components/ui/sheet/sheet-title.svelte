<script lang="ts">
    import {Dialog as SheetPrimitive} from "bits-ui";
    import {cn} from "$lib/utils.js";

    let {
        ref = $bindable(null),
        class: className,
        ...restProps
    }: SheetPrimitive.TitleProps = $props();
</script>

<SheetPrimitive.Title
        {...restProps}
        bind:ref
        class={cn("text-foreground font-semibold", className)}
        data-slot="sheet-title"
/>
