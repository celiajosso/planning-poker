<script lang="ts">
  import { toast } from "svelte-sonner";

  import { goto } from "$app/navigation";
  import BackToHome from "$lib/backToHome.svelte";

  import { Button } from "$lib/components/ui/button/index.js";
  import { Input } from "$lib/components/ui/input/index.js";

  import { z } from "zod";

  const loginSchema = z.object({
    username: z
      .string()
      .min(1, "Username is required")
      .min(3, "Username must be at least 3 characters")
      .max(50, "Username must be less than 50 characters")
      .regex(
        /^[a-zA-Z0-9_-]+$/,
        "Username can only contain letters, numbers, underscores and hyphens",
      ),
    password: z
      .string()
      .min(1, "Password is required")
      .min(8, "Password must be at least 8 characters")
      .max(100, "Password must be less than 100 characters")
      .regex(
        /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)/,
        "Password must contain at least one lowercase letter, one uppercase letter, and one number",
      ),
  });
  let username = $state("");
  let password = $state("");

  async function login(e: SubmitEvent) {
    let verify = loginSchema.safeParse({
      username,
      password,
    });

    if (!verify.success) {
      toast.error(verify.error.errors.map((e) => e.message).join(" / "));
      return;
    }
    fetch(
      `{window.location.protocol}//${import.meta.env.PROD ? window.location.host : "localhost:8080"}/api/login`,
      {
        method: "POST",
        body: JSON.stringify({
          username: verify.data.username,
          password: verify.data.password,
        }),
      },
    )
      .then((res) => {
        if (!res.ok) {
          throw new Error("Login failed");
        }
        return res.json();
      })
      .then(() => {
        toast.success("Login successful!");
      })
      .catch((err) => {
        toast.error(err.message || "An error occurred during login");
      });
    // await goto("/");
    return true;
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
