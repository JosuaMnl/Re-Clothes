<?php

namespace App\Http\Controllers\API;

use App\Helpers\ResponseFormatter;
use App\Http\Controllers\Controller;
use App\Http\Requests\Auth\LoginRequest;
use App\Http\Requests\Auth\RegisterRequest;
use App\Http\Requests\UserProfileRequest;
use Illuminate\Auth\Events\Registered;
use Illuminate\Http\Request;
use App\Models\User;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Hash;

class UserController extends Controller
{
    /**
     * Store a newly created resource in storage.
     */
    public function register(RegisterRequest $request): object
    {
        try{
            if ($request->roles == "1") {
                User::create([
                    'name' => $request->name,
                    'email' => $request->email,
                    'roles' => $request->roles,
                    'password' => Hash::make(
                        $request->password),
                ]);

                $user = User::where('email', $request->email)->first();
                $tokenResult = $user->createToken('authToken')->plainTextToken;

                event(new Registered($user));

                return ResponseFormatter::success([
                    'access_token' => $tokenResult,
                    'token_type' => 'Bearer',
                    'user' => $user,
                ], 'User Registered');
            }
        } catch (\Exception $e) {
            return ResponseFormatter::error([
                'message' => 'Something went wrong',
                'error' => $e->getMessage(),
            ], 'Authentication Failed', 500);
        }
    }

    public function login(LoginRequest $request): object
    {
        try{
            $credentials = request(['email', 'password']);
            if (!Auth::attempt($credentials)) {
                return ResponseFormatter::error([
                    'message' => 'Unauthorized',
                ], 'Authentication Failed', 500);
            }

            $user = User::where('email', $request->email)
                ->first();
            if (!Hash::check($request->password, $user->password, [])) {
                throw new \Exception('Invalid Credentials');
            }

            $tokenResult = $user->createToken('authToken')->plainTextToken;

            return ResponseFormatter::success([
                'access_token' => $tokenResult,
                'token_type' => 'Bearer',
                'user' => $user,
            ], 'Authenticated');
        } catch (\Exception $e) {
            return ResponseFormatter::error([
                'message' => 'Something went wrong',
                'error' => $e->getMessage(),
            ], 'Authentication Failed', 500);
        }
    }

    public function fetch(Request $request): object
    {
        return ResponseFormatter::success(
            $request->user(), 'Profile data retrieved successfully');
    }

    public function updateProfile(UserProfileRequest $request): object
    {

        $user = Auth::user();

        $data = $request->only([
            'name',
            'address',
            'phone_number',
        ]);

        if (!$user->update($data)) {
            return ResponseFormatter::error([
                'message' => 'Something went wrong',
            ], 'Profile update failed', 500);
        }

        return ResponseFormatter::success(
            $user, 'Profile updated successfully');
    }

    public function logout(Request $request): object
    {
        $token = $request->user()->currentAccessToken()->delete();

        return ResponseFormatter::success(
            $token, 'Token revoked successfully');
    }
}
