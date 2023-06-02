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
        Schema::create('cloths', function (Blueprint $table) {
            $table->uuid('id')
                ->primary();

            $table->uuid('cloth_image_id');
            $table->foreign('cloth_image_id')
                ->references('id')
                ->on('cloth_images');

            $table->string('type', 30);
            $table->string('description', 60)->nullable();
            $table->boolean('is_ready');
            $table->timestamps();
            $table->softDeletes();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('cloths');
    }
};
