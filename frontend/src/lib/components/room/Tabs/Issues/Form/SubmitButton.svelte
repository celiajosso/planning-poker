<script lang="ts">
  import { Game } from "$lib/Game.svelte";
  import { Icon } from "@steeze-ui/svelte-icon";
  import { Plus } from "@steeze-ui/heroicons";

  import * as Dialog from "$lib/components/ui/dialog/index.js";
  import * as Select from "$lib/components/ui/select/index.js";
  import { Button } from "$lib/components/ui/button/index.js";
  import { Label } from "$lib/components/ui/label";
  import { Input } from "$lib/components/ui/input";
  import { ScrollArea } from "$lib/components/ui/scroll-area";

  import { scores } from "../../../../../../routes/room/script";
  import FormRow from "../Form/FormRow.svelte";
  import FormRowScore from "../Form/FormRowScore.svelte";
</script>

<Dialog.Footer>
  <Button type="submit">Save changes</Button>
</Dialog.Footer>
