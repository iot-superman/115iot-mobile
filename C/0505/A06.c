// https://chatgpt.com/s/m_69f9582749e88191bbf1d5a3b55e31b6
#include <stdio.h>
#include <stdlib.h>
#include <stddef.h>   // ?【修正重點】offsetof 需要這個

// ================================
// Case 1：會產生 padding（原本題目）
// ================================
struct DataPadding {
    char name[18];   // 18 bytes
    int seniority;   // 4 bytes → 需要 4-byte 對齊
};

// ================================
// Case 2：剛好對齊（無 padding）
// ================================
struct DataNoPadding {
    char name[20];   // ? 20 bytes → 剛好 4 的倍數（不需 padding）
    int seniority;
};

// ================================
// Case 3：改順序（padding 跑到最後）
// ================================
struct DataReorder {
    int seniority;   // ? 先放大資料
    char name[18];   // ? 後面會補 padding
};

// ================================
// 印出 struct 資訊
// ================================
void print_struct_info() {

    printf("=====================================\n");

    // ===== Case 1 =====
    printf("[Case 1] 有 padding\n");
    printf("sizeof(struct DataPadding) = %zu\n", sizeof(struct DataPadding));

    // ?【修正】offsetof 需要 stddef.h
    printf("name offset = %zu\n", offsetof(struct DataPadding, name));
    printf("seniority offset = %zu\n", offsetof(struct DataPadding, seniority));
    printf("\n");

    // ===== Case 2 =====
    printf("[Case 2] 無 padding\n");
    printf("sizeof(struct DataNoPadding) = %zu\n", sizeof(struct DataNoPadding));
    printf("name offset = %zu\n", offsetof(struct DataNoPadding, name));
    printf("seniority offset = %zu\n", offsetof(struct DataNoPadding, seniority));
    printf("\n");

    // ===== Case 3 =====
    printf("[Case 3] 改順序\n");
    printf("sizeof(struct DataReorder) = %zu\n", sizeof(struct DataReorder));
    printf("seniority offset = %zu\n", offsetof(struct DataReorder, seniority));
    printf("name offset = %zu\n", offsetof(struct DataReorder, name));
    printf("\n");

    printf("=====================================\n");
}

// ================================
// main
// ================================
int main(void) {

    print_struct_info();

    struct DataPadding d1;

    printf("=== Memory Address Demo ===\n");

    printf("&d1           = %p\n", (void*)&d1);
    printf("&d1.name      = %p\n", (void*)&d1.name);
    printf("&d1.seniority = %p\n", (void*)&d1.seniority);

    // ? 觀察：seniority 會跳到對齊位置（padding 之後）

    system("pause"); // Windows
    return 0;
}
