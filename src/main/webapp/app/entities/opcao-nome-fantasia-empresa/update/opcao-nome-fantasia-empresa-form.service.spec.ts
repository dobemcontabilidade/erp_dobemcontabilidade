import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../opcao-nome-fantasia-empresa.test-samples';

import { OpcaoNomeFantasiaEmpresaFormService } from './opcao-nome-fantasia-empresa-form.service';

describe('OpcaoNomeFantasiaEmpresa Form Service', () => {
  let service: OpcaoNomeFantasiaEmpresaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OpcaoNomeFantasiaEmpresaFormService);
  });

  describe('Service methods', () => {
    describe('createOpcaoNomeFantasiaEmpresaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createOpcaoNomeFantasiaEmpresaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            ordem: expect.any(Object),
            selecionado: expect.any(Object),
            empresa: expect.any(Object),
          }),
        );
      });

      it('passing IOpcaoNomeFantasiaEmpresa should create a new form with FormGroup', () => {
        const formGroup = service.createOpcaoNomeFantasiaEmpresaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            ordem: expect.any(Object),
            selecionado: expect.any(Object),
            empresa: expect.any(Object),
          }),
        );
      });
    });

    describe('getOpcaoNomeFantasiaEmpresa', () => {
      it('should return NewOpcaoNomeFantasiaEmpresa for default OpcaoNomeFantasiaEmpresa initial value', () => {
        const formGroup = service.createOpcaoNomeFantasiaEmpresaFormGroup(sampleWithNewData);

        const opcaoNomeFantasiaEmpresa = service.getOpcaoNomeFantasiaEmpresa(formGroup) as any;

        expect(opcaoNomeFantasiaEmpresa).toMatchObject(sampleWithNewData);
      });

      it('should return NewOpcaoNomeFantasiaEmpresa for empty OpcaoNomeFantasiaEmpresa initial value', () => {
        const formGroup = service.createOpcaoNomeFantasiaEmpresaFormGroup();

        const opcaoNomeFantasiaEmpresa = service.getOpcaoNomeFantasiaEmpresa(formGroup) as any;

        expect(opcaoNomeFantasiaEmpresa).toMatchObject({});
      });

      it('should return IOpcaoNomeFantasiaEmpresa', () => {
        const formGroup = service.createOpcaoNomeFantasiaEmpresaFormGroup(sampleWithRequiredData);

        const opcaoNomeFantasiaEmpresa = service.getOpcaoNomeFantasiaEmpresa(formGroup) as any;

        expect(opcaoNomeFantasiaEmpresa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IOpcaoNomeFantasiaEmpresa should not enable id FormControl', () => {
        const formGroup = service.createOpcaoNomeFantasiaEmpresaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewOpcaoNomeFantasiaEmpresa should disable id FormControl', () => {
        const formGroup = service.createOpcaoNomeFantasiaEmpresaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
