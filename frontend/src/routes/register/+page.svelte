<script lang="ts">
  import { goto } from "$app/navigation";
  import BackToHome from "$lib/backToHome.svelte";
  import GdprInput from "$lib/components/home/GdprInput.svelte";

  import { Button } from "$lib/components/ui/button/index.js";
  import { Input } from "$lib/components/ui/input/index.js";

  let username = $state("");
  let password = $state("");
  let passwordConfirmation = $state("");
  let gdprChecked = $state(false);

  async function register(e: SubmitEvent) {
    e.preventDefault();
    await goto("/");
  }
</script>

<div
  class="w-dvw p-2 text-center text-[#333] flex flex-col h-dvh items-center justify-center bg-[#f4f4f9]"
>
  <BackToHome />
  <form
    class="h-[335px] border border-gray-300 p-4 rounded-xl mb-4"
    onsubmit={register}
  >
    <h2 class="text-center text-xl mb-8 font-semibold">Register</h2>
    <Input
      bind:value={username}
      placeholder="Username"
      required
      name="username"
      class="text-main-100 bg-main-900 w-full px-4 py-2 mb-6 text-sm outline-0 focus:border rounded-lg focus:border-main-800"
    />
    <Input
      bind:value={password}
      placeholder="Password"
      name="password"
      type="password"
      required
      class="text-main-100 bg-main-900 w-full px-4 py-2 mb-6 text-sm outline-0 focus:border rounded-lg focus:border-main-800"
    />
    <Input
      bind:value={passwordConfirmation}
      placeholder="Password confirmation"
      name="password"
      type="password"
      required
      class="text-main-100 bg-main-900 w-full px-4 py-2 mb-6 text-sm outline-0 focus:border rounded-lg focus:border-main-800"
    />
    <GdprInput id="create-room-gdpr" bind:checked={gdprChecked} />
    <Button type="submit" disabled={!gdprChecked}>Register</Button>
  </form>
</div>
