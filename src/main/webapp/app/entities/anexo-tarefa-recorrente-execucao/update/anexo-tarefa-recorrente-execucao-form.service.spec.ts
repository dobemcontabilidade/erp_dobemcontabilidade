import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../anexo-tarefa-recorrente-execucao.test-samples';

import { AnexoTarefaRecorrenteExecucaoFormService } from './anexo-tarefa-recorrente-execucao-form.service';

describe('AnexoTarefaRecorrenteExecucao Form Service', () => {
  let service: AnexoTarefaRecorrenteExecucaoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AnexoTarefaRecorrenteExecucaoFormService);
  });

  describe('Service methods', () => {
    describe('createAnexoTarefaRecorrenteExecucaoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAnexoTarefaRecorrenteExecucaoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            url: expect.any(Object),
            descricao: expect.any(Object),
            dataHoraUpload: expect.any(Object),
            tarefaRecorrenteExecucao: expect.any(Object),
          }),
        );
      });

      it('passing IAnexoTarefaRecorrenteExecucao should create a new form with FormGroup', () => {
        const formGroup = service.createAnexoTarefaRecorrenteExecucaoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            url: expect.any(Object),
            descricao: expect.any(Object),
            dataHoraUpload: expect.any(Object),
            tarefaRecorrenteExecucao: expect.any(Object),
          }),
        );
      });
    });

    describe('getAnexoTarefaRecorrenteExecucao', () => {
      it('should return NewAnexoTarefaRecorrenteExecucao for default AnexoTarefaRecorrenteExecucao initial value', () => {
        const formGroup = service.createAnexoTarefaRecorrenteExecucaoFormGroup(sampleWithNewData);

        const anexoTarefaRecorrenteExecucao = service.getAnexoTarefaRecorrenteExecucao(formGroup) as any;

        expect(anexoTarefaRecorrenteExecucao).toMatchObject(sampleWithNewData);
      });

      it('should return NewAnexoTarefaRecorrenteExecucao for empty AnexoTarefaRecorrenteExecucao initial value', () => {
        const formGroup = service.createAnexoTarefaRecorrenteExecucaoFormGroup();

        const anexoTarefaRecorrenteExecucao = service.getAnexoTarefaRecorrenteExecucao(formGroup) as any;

        expect(anexoTarefaRecorrenteExecucao).toMatchObject({});
      });

      it('should return IAnexoTarefaRecorrenteExecucao', () => {
        const formGroup = service.createAnexoTarefaRecorrenteExecucaoFormGroup(sampleWithRequiredData);

        const anexoTarefaRecorrenteExecucao = service.getAnexoTarefaRecorrenteExecucao(formGroup) as any;

        expect(anexoTarefaRecorrenteExecucao).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAnexoTarefaRecorrenteExecucao should not enable id FormControl', () => {
        const formGroup = service.createAnexoTarefaRecorrenteExecucaoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAnexoTarefaRecorrenteExecucao should disable id FormControl', () => {
        const formGroup = service.createAnexoTarefaRecorrenteExecucaoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
