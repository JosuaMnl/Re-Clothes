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
        Schema::create('cloth_images', function (Blueprint $table) {
            $table->uuid('id')
                ->primary();

            $table->string('original_image', 150);
            $table->integer('defects_status');
            $table->string('defects_proof', 150);
            $table->string('fabric_status', 25);
            $table->timestamps();
            $table->softDeletes();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('cloth_images');
    }
};
