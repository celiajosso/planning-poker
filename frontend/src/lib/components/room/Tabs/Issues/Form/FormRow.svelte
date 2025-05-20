<script lang="ts">
    import {Input} from "$lib/components/ui/input";
    import {Label} from "$lib/components/ui/label";

    export let field: string;
    export let value: string = "";
    export let required: boolean = true;
</script>

<div class="grid grid-cols-4 items-center gap-4">
    <Label class="text-right" for={field}
    >{field.charAt(0).toUpperCase() + field.slice(1)}</Label
    >
    <Input
            bind:value
            class="col-span-3 input"
            id={field}
            name={field}
            {required}
    />
</div>
