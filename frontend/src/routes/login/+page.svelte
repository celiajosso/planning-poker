<script lang="ts">
  import { goto } from "$app/navigation";
  import BackToHome from "$lib/backToHome.svelte";

  import { Button } from "$lib/components/ui/button/index.js";
  import { Input } from "$lib/components/ui/input/index.js";

  let username = $state("");
  let password = $state("");

  async function login(e: SubmitEvent) {
    e.preventDefault();
    await goto("/");
  }
</script>

<div
  class="w-dvw p-2 text-center text-[#333] flex flex-col h-dvh items-center justify-center bg-[#f4f4f9]"
>
  <BackToHome />
  <form
    onsubmit={login}
    class="h-[240px] border border-gray-300 p-4 rounded-xl mb-4"
  >
    <h2 class="text-center text-xl mb-8 font-semibold">Login</h2>
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
    <Button type="submit">Login</Button>
  </form>
  <a href="/register" class="text-sm">Don't have an account? Register here!</a>
</div>
