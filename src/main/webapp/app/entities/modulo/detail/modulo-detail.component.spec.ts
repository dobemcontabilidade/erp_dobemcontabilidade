import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ModuloDetailComponent } from './modulo-detail.component';

describe('Modulo Management Detail Component', () => {
  let comp: ModuloDetailComponent;
  let fixture: ComponentFixture<ModuloDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModuloDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ModuloDetailComponent,
              resolve: { modulo: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ModuloDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ModuloDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load modulo on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ModuloDetailComponent);

      // THEN
      expect(instance.modulo()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
