export interface ImageDetails {
  width: number;
  height: number;
  url: string;
}

export interface PinDTO {
  id: string;
  createdAt: string;
  link: string;
  title: string;
  description: string;
  boardId: string;
  boardSectionId: string;
  boardOwnerUsername: string;
  images: { [key: string]: ImageDetails };
  note: string;
  pinMetrics90d: { [key: string]: number };
  pinMetricsAllTime: { [key: string]: number };
}
