import {ErrorShowType} from "@@/plugin-request/request";

declare namespace API {
  export interface Response {
    success?: boolean;
    data?: any;
    errorCode?: string;
    errorMessage?: string;
    showType?: ErrorShowType;
    traceId?: string;
    host?: string;
    [key: string]: any;
  }

  export interface AccessToken {
    username?: string;
    token?: string;
    status? :string;
    issuer?: string;
    issuerAt?: string;
    expiresAt?: string;
  }

  export interface CurrentUser {
    avatar?: string;
    name?: string;
    title?: string;
    group?: string;
    signature?: string;
    tags?: {
      key: string;
      label: string;
    }[];
    userid?: string;
    access?: 'user' | 'guest' | 'admin';
    unreadCount?: number;
  }

  export interface NoticeIconData {
    id: string;
    key: string;
    avatar: string;
    title: string;
    datetime: string;
    type: string;
    read?: boolean;
    description: string;
    clickClose?: boolean;
    extra: any;
    status: string;
  }

  export interface SelectOption {
    value: string;
    label: string;
  }
}
