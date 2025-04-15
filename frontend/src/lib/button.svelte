<script lang="ts">
    let { onclick = undefined, href = undefined } = $props();
    let classes = "bg-[#0e9fc0] text-white border-none px-5 py-3 rounded-lg cursor-pointer m-2 hover:bg-[#0d8ba7]";
</script>


{#if href==undefined}
<button { onclick } class={classes}>
    <slot/>
</button>
{:else}
<a {href} class={classes}>
    <slot/>
</a>
{/if}