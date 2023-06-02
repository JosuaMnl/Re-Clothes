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
        Schema::create('payments', function (Blueprint $table) {
            $table->uuid('id')
                ->primary();

            $table->uuid('transaction_id');
            $table->foreign('transaction_id')
                ->references('id')
                ->on('transactions');

            $table->string('payment_method', 15)->default('Transfer');
            $table->float('payment_amount');
            $table->string('payment_proof', 150);
            $table->dateTime('payment_date');
            $table->timestamps();
            $table->softDeletes();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('payments');
    }
};
