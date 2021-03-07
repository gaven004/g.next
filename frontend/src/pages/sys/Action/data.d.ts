export interface TableListItem {
  id: string;
  resource: string;
  method: string;
  description: string;
  permitAll: boolean;
  status: string;
}

export interface TableListPagination {
  total: number;
  pageSize: number;
  current: number;
}

export interface TableListData {
  list: TableListItem[];
  pagination: Partial<TableListPagination>;
}

export interface TableListParams {
  id?: string;
  resource?: string;
  method?: string;
  description?: string;
  permitAll?: boolean;
  status?: string;
  pageSize?: number;
  currentPage?: number;
  filter?: { [key: string]: any[] };
  sorter?: { [key: string]: any };
}
