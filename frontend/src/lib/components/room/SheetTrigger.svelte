<script lang="ts">
  import { ChevronDoubleLeft } from "@steeze-ui/heroicons";

  import * as Sheet from "$lib/components/ui/sheet/index.js";
  import ButtonIcon from "$lib/ButtonIcon.svelte";
</script>

<Sheet.Trigger class="absolute top-5 right-5 color-gray-700">
  <ButtonIcon icon={ChevronDoubleLeft} size="size-6" theme="solid"></ButtonIcon>
</Sheet.Trigger>
