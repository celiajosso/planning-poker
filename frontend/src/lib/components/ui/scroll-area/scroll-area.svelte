<script lang="ts">
    import {ScrollArea as ScrollAreaPrimitive} from "bits-ui";
    import {Scrollbar} from "./index.js";
    import {cn, type WithoutChild} from "$lib/utils.js";

    let {
        ref = $bindable(null),
        class: className,
        orientation = "vertical",
        scrollbarXClasses = "",
        scrollbarYClasses = "",
        children,
        ...restProps
    }: WithoutChild<ScrollAreaPrimitive.RootProps> & {
        orientation?: "vertical" | "horizontal" | "both" | undefined;
        scrollbarXClasses?: string | undefined;
        scrollbarYClasses?: string | undefined;
    } = $props();
</script>

<ScrollAreaPrimitive.Root
        {...restProps}
        bind:ref
        class={cn("relative", className)}
        data-slot="scroll-area"
>
    <ScrollAreaPrimitive.Viewport
            class="ring-ring/10 dark:ring-ring/20 dark:outline-ring/40 outline-ring/50 size-full rounded-[inherit] transition-[color,box-shadow] focus-visible:outline-1 focus-visible:ring-4"
            data-slot="scroll-area-viewport"
    >
        {@render children?.()}
    </ScrollAreaPrimitive.Viewport>
    {#if orientation === "vertical" || orientation === "both"}
        <Scrollbar orientation="vertical" class={scrollbarYClasses}/>
    {/if}
    {#if orientation === "horizontal" || orientation === "both"}
        <Scrollbar orientation="horizontal" class={scrollbarXClasses}/>
    {/if}
    <ScrollAreaPrimitive.Corner/>
</ScrollAreaPrimitive.Root>
