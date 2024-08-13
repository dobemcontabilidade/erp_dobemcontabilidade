import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IGrupoCnae } from 'app/entities/grupo-cnae/grupo-cnae.model';
import { GrupoCnaeService } from 'app/entities/grupo-cnae/service/grupo-cnae.service';
import { ClasseCnaeService } from '../service/classe-cnae.service';
import { IClasseCnae } from '../classe-cnae.model';
import { ClasseCnaeFormService } from './classe-cnae-form.service';

import { ClasseCnaeUpdateComponent } from './classe-cnae-update.component';

describe('ClasseCnae Management Update Component', () => {
  let comp: ClasseCnaeUpdateComponent;
  let fixture: ComponentFixture<ClasseCnaeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let classeCnaeFormService: ClasseCnaeFormService;
  let classeCnaeService: ClasseCnaeService;
  let grupoCnaeService: GrupoCnaeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ClasseCnaeUpdateComponent],
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
      .overrideTemplate(ClasseCnaeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ClasseCnaeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    classeCnaeFormService = TestBed.inject(ClasseCnaeFormService);
    classeCnaeService = TestBed.inject(ClasseCnaeService);
    grupoCnaeService = TestBed.inject(GrupoCnaeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call GrupoCnae query and add missing value', () => {
      const classeCnae: IClasseCnae = { id: 456 };
      const grupo: IGrupoCnae = { id: 1579 };
      classeCnae.grupo = grupo;

      const grupoCnaeCollection: IGrupoCnae[] = [{ id: 17256 }];
      jest.spyOn(grupoCnaeService, 'query').mockReturnValue(of(new HttpResponse({ body: grupoCnaeCollection })));
      const additionalGrupoCnaes = [grupo];
      const expectedCollection: IGrupoCnae[] = [...additionalGrupoCnaes, ...grupoCnaeCollection];
      jest.spyOn(grupoCnaeService, 'addGrupoCnaeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ classeCnae });
      comp.ngOnInit();

      expect(grupoCnaeService.query).toHaveBeenCalled();
      expect(grupoCnaeService.addGrupoCnaeToCollectionIfMissing).toHaveBeenCalledWith(
        grupoCnaeCollection,
        ...additionalGrupoCnaes.map(expect.objectContaining),
      );
      expect(comp.grupoCnaesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const classeCnae: IClasseCnae = { id: 456 };
      const grupo: IGrupoCnae = { id: 26224 };
      classeCnae.grupo = grupo;

      activatedRoute.data = of({ classeCnae });
      comp.ngOnInit();

      expect(comp.grupoCnaesSharedCollection).toContain(grupo);
      expect(comp.classeCnae).toEqual(classeCnae);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IClasseCnae>>();
      const classeCnae = { id: 123 };
      jest.spyOn(classeCnaeFormService, 'getClasseCnae').mockReturnValue(classeCnae);
      jest.spyOn(classeCnaeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ classeCnae });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: classeCnae }));
      saveSubject.complete();

      // THEN
      expect(classeCnaeFormService.getClasseCnae).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(classeCnaeService.update).toHaveBeenCalledWith(expect.objectContaining(classeCnae));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IClasseCnae>>();
      const classeCnae = { id: 123 };
      jest.spyOn(classeCnaeFormService, 'getClasseCnae').mockReturnValue({ id: null });
      jest.spyOn(classeCnaeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ classeCnae: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: classeCnae }));
      saveSubject.complete();

      // THEN
      expect(classeCnaeFormService.getClasseCnae).toHaveBeenCalled();
      expect(classeCnaeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IClasseCnae>>();
      const classeCnae = { id: 123 };
      jest.spyOn(classeCnaeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ classeCnae });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(classeCnaeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareGrupoCnae', () => {
      it('Should forward to grupoCnaeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(grupoCnaeService, 'compareGrupoCnae');
        comp.compareGrupoCnae(entity, entity2);
        expect(grupoCnaeService.compareGrupoCnae).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
