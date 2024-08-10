import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { AtividadeEmpresaService } from '../service/atividade-empresa.service';
import { IAtividadeEmpresa } from '../atividade-empresa.model';
import { AtividadeEmpresaFormService } from './atividade-empresa-form.service';

import { AtividadeEmpresaUpdateComponent } from './atividade-empresa-update.component';

describe('AtividadeEmpresa Management Update Component', () => {
  let comp: AtividadeEmpresaUpdateComponent;
  let fixture: ComponentFixture<AtividadeEmpresaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let atividadeEmpresaFormService: AtividadeEmpresaFormService;
  let atividadeEmpresaService: AtividadeEmpresaService;
  let empresaService: EmpresaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AtividadeEmpresaUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(AtividadeEmpresaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AtividadeEmpresaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    atividadeEmpresaFormService = TestBed.inject(AtividadeEmpresaFormService);
    atividadeEmpresaService = TestBed.inject(AtividadeEmpresaService);
    empresaService = TestBed.inject(EmpresaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Empresa query and add missing value', () => {
      const atividadeEmpresa: IAtividadeEmpresa = { id: 456 };
      const empresa: IEmpresa = { id: 8571 };
      atividadeEmpresa.empresa = empresa;

      const empresaCollection: IEmpresa[] = [{ id: 32451 }];
      jest.spyOn(empresaService, 'query').mockReturnValue(of(new HttpResponse({ body: empresaCollection })));
      const additionalEmpresas = [empresa];
      const expectedCollection: IEmpresa[] = [...additionalEmpresas, ...empresaCollection];
      jest.spyOn(empresaService, 'addEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ atividadeEmpresa });
      comp.ngOnInit();

      expect(empresaService.query).toHaveBeenCalled();
      expect(empresaService.addEmpresaToCollectionIfMissing).toHaveBeenCalledWith(
        empresaCollection,
        ...additionalEmpresas.map(expect.objectContaining),
      );
      expect(comp.empresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const atividadeEmpresa: IAtividadeEmpresa = { id: 456 };
      const empresa: IEmpresa = { id: 5636 };
      atividadeEmpresa.empresa = empresa;

      activatedRoute.data = of({ atividadeEmpresa });
      comp.ngOnInit();

      expect(comp.empresasSharedCollection).toContain(empresa);
      expect(comp.atividadeEmpresa).toEqual(atividadeEmpresa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAtividadeEmpresa>>();
      const atividadeEmpresa = { id: 123 };
      jest.spyOn(atividadeEmpresaFormService, 'getAtividadeEmpresa').mockReturnValue(atividadeEmpresa);
      jest.spyOn(atividadeEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ atividadeEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: atividadeEmpresa }));
      saveSubject.complete();

      // THEN
      expect(atividadeEmpresaFormService.getAtividadeEmpresa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(atividadeEmpresaService.update).toHaveBeenCalledWith(expect.objectContaining(atividadeEmpresa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAtividadeEmpresa>>();
      const atividadeEmpresa = { id: 123 };
      jest.spyOn(atividadeEmpresaFormService, 'getAtividadeEmpresa').mockReturnValue({ id: null });
      jest.spyOn(atividadeEmpresaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ atividadeEmpresa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: atividadeEmpresa }));
      saveSubject.complete();

      // THEN
      expect(atividadeEmpresaFormService.getAtividadeEmpresa).toHaveBeenCalled();
      expect(atividadeEmpresaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAtividadeEmpresa>>();
      const atividadeEmpresa = { id: 123 };
      jest.spyOn(atividadeEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ atividadeEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(atividadeEmpresaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEmpresa', () => {
      it('Should forward to empresaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(empresaService, 'compareEmpresa');
        comp.compareEmpresa(entity, entity2);
        expect(empresaService.compareEmpresa).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
