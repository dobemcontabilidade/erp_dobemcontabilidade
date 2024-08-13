import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IGrupoAcessoPadrao } from '../grupo-acesso-padrao.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../grupo-acesso-padrao.test-samples';

import { GrupoAcessoPadraoService } from './grupo-acesso-padrao.service';

const requireRestSample: IGrupoAcessoPadrao = {
  ...sampleWithRequiredData,
};

describe('GrupoAcessoPadrao Service', () => {
  let service: GrupoAcessoPadraoService;
  let httpMock: HttpTestingController;
  let expectedResult: IGrupoAcessoPadrao | IGrupoAcessoPadrao[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(GrupoAcessoPadraoService);
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

    it('should create a GrupoAcessoPadrao', () => {
      const grupoAcessoPadrao = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(grupoAcessoPadrao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a GrupoAcessoPadrao', () => {
      const grupoAcessoPadrao = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(grupoAcessoPadrao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a GrupoAcessoPadrao', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of GrupoAcessoPadrao', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a GrupoAcessoPadrao', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addGrupoAcessoPadraoToCollectionIfMissing', () => {
      it('should add a GrupoAcessoPadrao to an empty array', () => {
        const grupoAcessoPadrao: IGrupoAcessoPadrao = sampleWithRequiredData;
        expectedResult = service.addGrupoAcessoPadraoToCollectionIfMissing([], grupoAcessoPadrao);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(grupoAcessoPadrao);
      });

      it('should not add a GrupoAcessoPadrao to an array that contains it', () => {
        const grupoAcessoPadrao: IGrupoAcessoPadrao = sampleWithRequiredData;
        const grupoAcessoPadraoCollection: IGrupoAcessoPadrao[] = [
          {
            ...grupoAcessoPadrao,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addGrupoAcessoPadraoToCollectionIfMissing(grupoAcessoPadraoCollection, grupoAcessoPadrao);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a GrupoAcessoPadrao to an array that doesn't contain it", () => {
        const grupoAcessoPadrao: IGrupoAcessoPadrao = sampleWithRequiredData;
        const grupoAcessoPadraoCollection: IGrupoAcessoPadrao[] = [sampleWithPartialData];
        expectedResult = service.addGrupoAcessoPadraoToCollectionIfMissing(grupoAcessoPadraoCollection, grupoAcessoPadrao);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(grupoAcessoPadrao);
      });

      it('should add only unique GrupoAcessoPadrao to an array', () => {
        const grupoAcessoPadraoArray: IGrupoAcessoPadrao[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const grupoAcessoPadraoCollection: IGrupoAcessoPadrao[] = [sampleWithRequiredData];
        expectedResult = service.addGrupoAcessoPadraoToCollectionIfMissing(grupoAcessoPadraoCollection, ...grupoAcessoPadraoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const grupoAcessoPadrao: IGrupoAcessoPadrao = sampleWithRequiredData;
        const grupoAcessoPadrao2: IGrupoAcessoPadrao = sampleWithPartialData;
        expectedResult = service.addGrupoAcessoPadraoToCollectionIfMissing([], grupoAcessoPadrao, grupoAcessoPadrao2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(grupoAcessoPadrao);
        expect(expectedResult).toContain(grupoAcessoPadrao2);
      });

      it('should accept null and undefined values', () => {
        const grupoAcessoPadrao: IGrupoAcessoPadrao = sampleWithRequiredData;
        expectedResult = service.addGrupoAcessoPadraoToCollectionIfMissing([], null, grupoAcessoPadrao, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(grupoAcessoPadrao);
      });

      it('should return initial array if no GrupoAcessoPadrao is added', () => {
        const grupoAcessoPadraoCollection: IGrupoAcessoPadrao[] = [sampleWithRequiredData];
        expectedResult = service.addGrupoAcessoPadraoToCollectionIfMissing(grupoAcessoPadraoCollection, undefined, null);
        expect(expectedResult).toEqual(grupoAcessoPadraoCollection);
      });
    });

    describe('compareGrupoAcessoPadrao', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareGrupoAcessoPadrao(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareGrupoAcessoPadrao(entity1, entity2);
        const compareResult2 = service.compareGrupoAcessoPadrao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareGrupoAcessoPadrao(entity1, entity2);
        const compareResult2 = service.compareGrupoAcessoPadrao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareGrupoAcessoPadrao(entity1, entity2);
        const compareResult2 = service.compareGrupoAcessoPadrao(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
