import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { IImposto } from 'app/entities/imposto/imposto.model';
import { ImpostoService } from 'app/entities/imposto/service/imposto.service';
import { IImpostoEmpresa } from '../imposto-empresa.model';
import { ImpostoEmpresaService } from '../service/imposto-empresa.service';
import { ImpostoEmpresaFormService } from './imposto-empresa-form.service';

import { ImpostoEmpresaUpdateComponent } from './imposto-empresa-update.component';

describe('ImpostoEmpresa Management Update Component', () => {
  let comp: ImpostoEmpresaUpdateComponent;
  let fixture: ComponentFixture<ImpostoEmpresaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let impostoEmpresaFormService: ImpostoEmpresaFormService;
  let impostoEmpresaService: ImpostoEmpresaService;
  let empresaService: EmpresaService;
  let impostoService: ImpostoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ImpostoEmpresaUpdateComponent],
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
      .overrideTemplate(ImpostoEmpresaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ImpostoEmpresaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    impostoEmpresaFormService = TestBed.inject(ImpostoEmpresaFormService);
    impostoEmpresaService = TestBed.inject(ImpostoEmpresaService);
    empresaService = TestBed.inject(EmpresaService);
    impostoService = TestBed.inject(ImpostoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Empresa query and add missing value', () => {
      const impostoEmpresa: IImpostoEmpresa = { id: 456 };
      const empresa: IEmpresa = { id: 17826 };
      impostoEmpresa.empresa = empresa;

      const empresaCollection: IEmpresa[] = [{ id: 21122 }];
      jest.spyOn(empresaService, 'query').mockReturnValue(of(new HttpResponse({ body: empresaCollection })));
      const additionalEmpresas = [empresa];
      const expectedCollection: IEmpresa[] = [...additionalEmpresas, ...empresaCollection];
      jest.spyOn(empresaService, 'addEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ impostoEmpresa });
      comp.ngOnInit();

      expect(empresaService.query).toHaveBeenCalled();
      expect(empresaService.addEmpresaToCollectionIfMissing).toHaveBeenCalledWith(
        empresaCollection,
        ...additionalEmpresas.map(expect.objectContaining),
      );
      expect(comp.empresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Imposto query and add missing value', () => {
      const impostoEmpresa: IImpostoEmpresa = { id: 456 };
      const imposto: IImposto = { id: 18608 };
      impostoEmpresa.imposto = imposto;

      const impostoCollection: IImposto[] = [{ id: 23929 }];
      jest.spyOn(impostoService, 'query').mockReturnValue(of(new HttpResponse({ body: impostoCollection })));
      const additionalImpostos = [imposto];
      const expectedCollection: IImposto[] = [...additionalImpostos, ...impostoCollection];
      jest.spyOn(impostoService, 'addImpostoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ impostoEmpresa });
      comp.ngOnInit();

      expect(impostoService.query).toHaveBeenCalled();
      expect(impostoService.addImpostoToCollectionIfMissing).toHaveBeenCalledWith(
        impostoCollection,
        ...additionalImpostos.map(expect.objectContaining),
      );
      expect(comp.impostosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const impostoEmpresa: IImpostoEmpresa = { id: 456 };
      const empresa: IEmpresa = { id: 31565 };
      impostoEmpresa.empresa = empresa;
      const imposto: IImposto = { id: 6928 };
      impostoEmpresa.imposto = imposto;

      activatedRoute.data = of({ impostoEmpresa });
      comp.ngOnInit();

      expect(comp.empresasSharedCollection).toContain(empresa);
      expect(comp.impostosSharedCollection).toContain(imposto);
      expect(comp.impostoEmpresa).toEqual(impostoEmpresa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IImpostoEmpresa>>();
      const impostoEmpresa = { id: 123 };
      jest.spyOn(impostoEmpresaFormService, 'getImpostoEmpresa').mockReturnValue(impostoEmpresa);
      jest.spyOn(impostoEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ impostoEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: impostoEmpresa }));
      saveSubject.complete();

      // THEN
      expect(impostoEmpresaFormService.getImpostoEmpresa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(impostoEmpresaService.update).toHaveBeenCalledWith(expect.objectContaining(impostoEmpresa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IImpostoEmpresa>>();
      const impostoEmpresa = { id: 123 };
      jest.spyOn(impostoEmpresaFormService, 'getImpostoEmpresa').mockReturnValue({ id: null });
      jest.spyOn(impostoEmpresaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ impostoEmpresa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: impostoEmpresa }));
      saveSubject.complete();

      // THEN
      expect(impostoEmpresaFormService.getImpostoEmpresa).toHaveBeenCalled();
      expect(impostoEmpresaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IImpostoEmpresa>>();
      const impostoEmpresa = { id: 123 };
      jest.spyOn(impostoEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ impostoEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(impostoEmpresaService.update).toHaveBeenCalled();
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

    describe('compareImposto', () => {
      it('Should forward to impostoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(impostoService, 'compareImposto');
        comp.compareImposto(entity, entity2);
        expect(impostoService.compareImposto).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
