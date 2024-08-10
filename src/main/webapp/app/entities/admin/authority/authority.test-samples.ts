import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: '4805d434-2294-442c-a324-836f34001ead',
};

export const sampleWithPartialData: IAuthority = {
  name: '405ff7d2-6316-4709-933f-2b0c14276f0a',
};

export const sampleWithFullData: IAuthority = {
  name: '1c2b89b8-6392-4799-968e-5242e10fad05',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
