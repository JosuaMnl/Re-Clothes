<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('transaction_items', function (Blueprint $table) {
            $table->uuid('id')
                ->primary();

            $table->uuid('transaction_id');
            $table->foreign('transaction_id')
                ->references('id')
                ->on('transactions');

            $table->uuid('cloth_id');
            $table->foreign('cloth_id')
                ->references('id')
                ->on('cloths');

            $table->uuid('user_id');
            $table->foreign('user_id')
                ->references('id')
                ->on('users');

            $table->float('weight');
            $table->integer('quantity');
            $table->timestamps();
            $table->softDeletes();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('transaction_items');
    }
};
