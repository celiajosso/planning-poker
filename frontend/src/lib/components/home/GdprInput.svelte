<script lang="ts">
  import { Checkbox } from "$lib/components/ui/checkbox/index.js";
  import Label from "$lib/components/ui/label/label.svelte";
  import { Icon } from "@steeze-ui/svelte-icon";
  import { ArrowTopRightOnSquare } from "@steeze-ui/heroicons";

  export let checked: boolean;
  export let id: string;
</script>

<div class="flex items-center justify-center space-x-2 mb-4">
  <Checkbox {id} bind:checked aria-labelledby="{id}-label" />
  <Label
    id="{id}-label"
    for={id}
    class="text-sm gap-1 font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
  >
    I accept the
    <a
      href="/policies/CELEX_32016R0679_EN_TXT.pdf"
      target="_blank"
      rel="noopener noreferrer"
      class="underline hover:text-gray-500 hover:underline transition duration-150"
    >
      personal data policy
      <Icon
        class="inline align-middle ml-1 size-3 text-gray-800"
        src={ArrowTopRightOnSquare}
        theme="solid"
      />
    </a>
  </Label>
</div>
