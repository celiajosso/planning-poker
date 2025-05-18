<script lang="ts">
  import { Input } from "$lib/components/ui/input";
  import { Label } from "$lib/components/ui/label";

  export let field: string;
  export let value: string = "";
  export let required: boolean = true;
</script>

<div class="grid grid-cols-4 items-center gap-4">
  <Label for={field} class="text-right"
    >{field.charAt(0).toUpperCase() + field.slice(1)}</Label
  >
  <Input
    id={field}
    name={field}
    bind:value
    {required}
    class="col-span-3 input"
  />
</div>
