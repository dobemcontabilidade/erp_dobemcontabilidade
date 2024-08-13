import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IPerfilRedeSocial } from '../perfil-rede-social.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../perfil-rede-social.test-samples';

import { PerfilRedeSocialService } from './perfil-rede-social.service';

const requireRestSample: IPerfilRedeSocial = {
  ...sampleWithRequiredData,
};

describe('PerfilRedeSocial Service', () => {
  let service: PerfilRedeSocialService;
  let httpMock: HttpTestingController;
  let expectedResult: IPerfilRedeSocial | IPerfilRedeSocial[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(PerfilRedeSocialService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a PerfilRedeSocial', () => {
      const perfilRedeSocial = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(perfilRedeSocial).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PerfilRedeSocial', () => {
      const perfilRedeSocial = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(perfilRedeSocial).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PerfilRedeSocial', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PerfilRedeSocial', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PerfilRedeSocial', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPerfilRedeSocialToCollectionIfMissing', () => {
      it('should add a PerfilRedeSocial to an empty array', () => {
        const perfilRedeSocial: IPerfilRedeSocial = sampleWithRequiredData;
        expectedResult = service.addPerfilRedeSocialToCollectionIfMissing([], perfilRedeSocial);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(perfilRedeSocial);
      });

      it('should not add a PerfilRedeSocial to an array that contains it', () => {
        const perfilRedeSocial: IPerfilRedeSocial = sampleWithRequiredData;
        const perfilRedeSocialCollection: IPerfilRedeSocial[] = [
          {
            ...perfilRedeSocial,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPerfilRedeSocialToCollectionIfMissing(perfilRedeSocialCollection, perfilRedeSocial);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PerfilRedeSocial to an array that doesn't contain it", () => {
        const perfilRedeSocial: IPerfilRedeSocial = sampleWithRequiredData;
        const perfilRedeSocialCollection: IPerfilRedeSocial[] = [sampleWithPartialData];
        expectedResult = service.addPerfilRedeSocialToCollectionIfMissing(perfilRedeSocialCollection, perfilRedeSocial);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(perfilRedeSocial);
      });

      it('should add only unique PerfilRedeSocial to an array', () => {
        const perfilRedeSocialArray: IPerfilRedeSocial[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const perfilRedeSocialCollection: IPerfilRedeSocial[] = [sampleWithRequiredData];
        expectedResult = service.addPerfilRedeSocialToCollectionIfMissing(perfilRedeSocialCollection, ...perfilRedeSocialArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const perfilRedeSocial: IPerfilRedeSocial = sampleWithRequiredData;
        const perfilRedeSocial2: IPerfilRedeSocial = sampleWithPartialData;
        expectedResult = service.addPerfilRedeSocialToCollectionIfMissing([], perfilRedeSocial, perfilRedeSocial2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(perfilRedeSocial);
        expect(expectedResult).toContain(perfilRedeSocial2);
      });

      it('should accept null and undefined values', () => {
        const perfilRedeSocial: IPerfilRedeSocial = sampleWithRequiredData;
        expectedResult = service.addPerfilRedeSocialToCollectionIfMissing([], null, perfilRedeSocial, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(perfilRedeSocial);
      });

      it('should return initial array if no PerfilRedeSocial is added', () => {
        const perfilRedeSocialCollection: IPerfilRedeSocial[] = [sampleWithRequiredData];
        expectedResult = service.addPerfilRedeSocialToCollectionIfMissing(perfilRedeSocialCollection, undefined, null);
        expect(expectedResult).toEqual(perfilRedeSocialCollection);
      });
    });

    describe('comparePerfilRedeSocial', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePerfilRedeSocial(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePerfilRedeSocial(entity1, entity2);
        const compareResult2 = service.comparePerfilRedeSocial(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePerfilRedeSocial(entity1, entity2);
        const compareResult2 = service.comparePerfilRedeSocial(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePerfilRedeSocial(entity1, entity2);
        const compareResult2 = service.comparePerfilRedeSocial(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
