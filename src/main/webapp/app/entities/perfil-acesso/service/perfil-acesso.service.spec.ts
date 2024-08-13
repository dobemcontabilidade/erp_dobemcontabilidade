import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IPerfilAcesso } from '../perfil-acesso.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../perfil-acesso.test-samples';

import { PerfilAcessoService } from './perfil-acesso.service';

const requireRestSample: IPerfilAcesso = {
  ...sampleWithRequiredData,
};

describe('PerfilAcesso Service', () => {
  let service: PerfilAcessoService;
  let httpMock: HttpTestingController;
  let expectedResult: IPerfilAcesso | IPerfilAcesso[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(PerfilAcessoService);
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

    it('should create a PerfilAcesso', () => {
      const perfilAcesso = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(perfilAcesso).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PerfilAcesso', () => {
      const perfilAcesso = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(perfilAcesso).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PerfilAcesso', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PerfilAcesso', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PerfilAcesso', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPerfilAcessoToCollectionIfMissing', () => {
      it('should add a PerfilAcesso to an empty array', () => {
        const perfilAcesso: IPerfilAcesso = sampleWithRequiredData;
        expectedResult = service.addPerfilAcessoToCollectionIfMissing([], perfilAcesso);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(perfilAcesso);
      });

      it('should not add a PerfilAcesso to an array that contains it', () => {
        const perfilAcesso: IPerfilAcesso = sampleWithRequiredData;
        const perfilAcessoCollection: IPerfilAcesso[] = [
          {
            ...perfilAcesso,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPerfilAcessoToCollectionIfMissing(perfilAcessoCollection, perfilAcesso);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PerfilAcesso to an array that doesn't contain it", () => {
        const perfilAcesso: IPerfilAcesso = sampleWithRequiredData;
        const perfilAcessoCollection: IPerfilAcesso[] = [sampleWithPartialData];
        expectedResult = service.addPerfilAcessoToCollectionIfMissing(perfilAcessoCollection, perfilAcesso);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(perfilAcesso);
      });

      it('should add only unique PerfilAcesso to an array', () => {
        const perfilAcessoArray: IPerfilAcesso[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const perfilAcessoCollection: IPerfilAcesso[] = [sampleWithRequiredData];
        expectedResult = service.addPerfilAcessoToCollectionIfMissing(perfilAcessoCollection, ...perfilAcessoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const perfilAcesso: IPerfilAcesso = sampleWithRequiredData;
        const perfilAcesso2: IPerfilAcesso = sampleWithPartialData;
        expectedResult = service.addPerfilAcessoToCollectionIfMissing([], perfilAcesso, perfilAcesso2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(perfilAcesso);
        expect(expectedResult).toContain(perfilAcesso2);
      });

      it('should accept null and undefined values', () => {
        const perfilAcesso: IPerfilAcesso = sampleWithRequiredData;
        expectedResult = service.addPerfilAcessoToCollectionIfMissing([], null, perfilAcesso, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(perfilAcesso);
      });

      it('should return initial array if no PerfilAcesso is added', () => {
        const perfilAcessoCollection: IPerfilAcesso[] = [sampleWithRequiredData];
        expectedResult = service.addPerfilAcessoToCollectionIfMissing(perfilAcessoCollection, undefined, null);
        expect(expectedResult).toEqual(perfilAcessoCollection);
      });
    });

    describe('comparePerfilAcesso', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePerfilAcesso(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePerfilAcesso(entity1, entity2);
        const compareResult2 = service.comparePerfilAcesso(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePerfilAcesso(entity1, entity2);
        const compareResult2 = service.comparePerfilAcesso(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePerfilAcesso(entity1, entity2);
        const compareResult2 = service.comparePerfilAcesso(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
