import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../criterio-avaliacao.test-samples';

import { CriterioAvaliacaoFormService } from './criterio-avaliacao-form.service';

describe('CriterioAvaliacao Form Service', () => {
  let service: CriterioAvaliacaoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CriterioAvaliacaoFormService);
  });

  describe('Service methods', () => {
    describe('createCriterioAvaliacaoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCriterioAvaliacaoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
          }),
        );
      });

      it('passing ICriterioAvaliacao should create a new form with FormGroup', () => {
        const formGroup = service.createCriterioAvaliacaoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
          }),
        );
      });
    });

    describe('getCriterioAvaliacao', () => {
      it('should return NewCriterioAvaliacao for default CriterioAvaliacao initial value', () => {
        const formGroup = service.createCriterioAvaliacaoFormGroup(sampleWithNewData);

        const criterioAvaliacao = service.getCriterioAvaliacao(formGroup) as any;

        expect(criterioAvaliacao).toMatchObject(sampleWithNewData);
      });

      it('should return NewCriterioAvaliacao for empty CriterioAvaliacao initial value', () => {
        const formGroup = service.createCriterioAvaliacaoFormGroup();

        const criterioAvaliacao = service.getCriterioAvaliacao(formGroup) as any;

        expect(criterioAvaliacao).toMatchObject({});
      });

      it('should return ICriterioAvaliacao', () => {
        const formGroup = service.createCriterioAvaliacaoFormGroup(sampleWithRequiredData);

        const criterioAvaliacao = service.getCriterioAvaliacao(formGroup) as any;

        expect(criterioAvaliacao).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICriterioAvaliacao should not enable id FormControl', () => {
        const formGroup = service.createCriterioAvaliacaoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCriterioAvaliacao should disable id FormControl', () => {
        const formGroup = service.createCriterioAvaliacaoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
