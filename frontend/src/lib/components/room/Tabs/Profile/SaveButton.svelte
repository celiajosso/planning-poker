<script lang="ts">
  import { Game } from "$lib/Game.svelte";
  import { Icon } from "@steeze-ui/svelte-icon";
  import { Check } from "@steeze-ui/heroicons";

  import { Button } from "$lib/components/ui/button/index.js";
</script>

<div class="flex justify-center w-full">
  <Button
    builders={[]}
    class="outline flex flex-row gap-1 p-4 mx-auto m-2"
    onclick={() => Game.updateUser(Game.storage.user)}
  >
    <Icon class="color-gray-800 size-5" src={Check} theme="solid" />
    Save
  </Button>
</div>
