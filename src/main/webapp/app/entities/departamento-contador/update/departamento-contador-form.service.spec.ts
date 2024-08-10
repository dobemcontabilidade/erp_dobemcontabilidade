import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../departamento-contador.test-samples';

import { DepartamentoContadorFormService } from './departamento-contador-form.service';

describe('DepartamentoContador Form Service', () => {
  let service: DepartamentoContadorFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DepartamentoContadorFormService);
  });

  describe('Service methods', () => {
    describe('createDepartamentoContadorFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDepartamentoContadorFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            percentualExperiencia: expect.any(Object),
            descricaoExperiencia: expect.any(Object),
            pontuacaoEntrevista: expect.any(Object),
            pontuacaoAvaliacao: expect.any(Object),
            departamento: expect.any(Object),
            contador: expect.any(Object),
          }),
        );
      });

      it('passing IDepartamentoContador should create a new form with FormGroup', () => {
        const formGroup = service.createDepartamentoContadorFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            percentualExperiencia: expect.any(Object),
            descricaoExperiencia: expect.any(Object),
            pontuacaoEntrevista: expect.any(Object),
            pontuacaoAvaliacao: expect.any(Object),
            departamento: expect.any(Object),
            contador: expect.any(Object),
          }),
        );
      });
    });

    describe('getDepartamentoContador', () => {
      it('should return NewDepartamentoContador for default DepartamentoContador initial value', () => {
        const formGroup = service.createDepartamentoContadorFormGroup(sampleWithNewData);

        const departamentoContador = service.getDepartamentoContador(formGroup) as any;

        expect(departamentoContador).toMatchObject(sampleWithNewData);
      });

      it('should return NewDepartamentoContador for empty DepartamentoContador initial value', () => {
        const formGroup = service.createDepartamentoContadorFormGroup();

        const departamentoContador = service.getDepartamentoContador(formGroup) as any;

        expect(departamentoContador).toMatchObject({});
      });

      it('should return IDepartamentoContador', () => {
        const formGroup = service.createDepartamentoContadorFormGroup(sampleWithRequiredData);

        const departamentoContador = service.getDepartamentoContador(formGroup) as any;

        expect(departamentoContador).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDepartamentoContador should not enable id FormControl', () => {
        const formGroup = service.createDepartamentoContadorFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDepartamentoContador should disable id FormControl', () => {
        const formGroup = service.createDepartamentoContadorFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
